package com.mateo.crudapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mateo.crudapi.models.User;
import com.mateo.crudapi.services.UserServiceImpl;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "${cors.allowedOrigins}")
public class UserController {

    @Value("${cors.allowedOrigins}")
    private String allowedOrigins;

    @Value("${cors.allowedMethods}")
    private String allowedMethods;

    @Value("${cors.allowedHeaders}")
    private String allowedHeaders;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping("/users")
    ResponseEntity<List<User>> findAll() {
        return this.userServiceImpl.findAll();
    }

    @PostMapping("/save-user")
    ResponseEntity<User> save(@RequestBody User user) {
        return this.userServiceImpl.save(user);
    }

    @PutMapping("/update-user/{id}")
    ResponseEntity update(@PathVariable Long id, @RequestBody User user) {
        return this.userServiceImpl.update(id, user);
    }

    @DeleteMapping("/delete-user/{id}")
    ResponseEntity delete(@PathVariable Long id) {
        return this.userServiceImpl.deleteById(id);
    }

    @GetMapping("find-user/{id}")
    ResponseEntity findById(@PathVariable Long id) {
        return this.userServiceImpl.findById(id);
    }

    @DeleteMapping("/delete-post/{id}")
    ResponseEntity deletePost(@PathVariable Long id) {
        return this.userServiceImpl.deletePostById(id);
    }
}
