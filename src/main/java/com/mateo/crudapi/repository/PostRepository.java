package com.mateo.crudapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mateo.crudapi.models.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
