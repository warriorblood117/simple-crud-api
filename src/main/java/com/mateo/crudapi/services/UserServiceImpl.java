package com.mateo.crudapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mateo.crudapi.models.Post;
import com.mateo.crudapi.models.User;
import com.mateo.crudapi.repository.UserRepository;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<List<User>> findAll() {
        List<User> users = this.userRepository.findAll();
        Optional<List<User>> optionalUsers = Optional.of(users);

        return optionalUsers.filter(u -> !u.isEmpty())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }

    @Override
    public ResponseEntity<User> save(User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userRepository.save(user));
    }

    @Override
    public ResponseEntity<User> update(Long id, User userDetails) {
        return this.userRepository.findById(id)
                .map(user -> {
                    user.setName(userDetails.getName());

                    // Update posts individually
                    for (Post userDetailsPost : userDetails.getPosts()) {
                        Post existingPost = user.getPosts().stream()
                                .filter(p -> p.getId().equals(userDetailsPost.getId()))
                                .findFirst().orElse(null);
                        if (existingPost != null) {
                            existingPost.setTitle(userDetailsPost.getTitle());
                            existingPost.setContent(userDetailsPost.getContent());
                        } else {
                            user.getPosts().add(userDetailsPost);
                        }
                    }

                    return Optional.of(userRepository.save(user));
                })
                .map(updateUser -> ResponseEntity.ok(updateUser.get()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> deleteById(Long id) {
        this.userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity findById(Long id) {
        return this.userRepository.findById(id)
                .map(userFounded -> ResponseEntity.ok(userFounded))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity deletePostById(Long idPost) {
        User user = userRepository.findByPostsId(idPost).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
        }

        user.getPosts().removeIf(post -> post.getId().equals(idPost));
        userRepository.save(user); // Guardar los cambios en la base de datos

        return ResponseEntity.ok("Post deleted successfully");
    }

}
