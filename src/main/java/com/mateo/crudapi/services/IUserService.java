package com.mateo.crudapi.services;

import org.springframework.http.ResponseEntity;

import com.mateo.crudapi.models.User;

public interface IUserService {
    ResponseEntity findAll();
    ResponseEntity findById(Long id);
    ResponseEntity save(User user);
    ResponseEntity update(Long id, User userDetails);
    ResponseEntity deleteById(Long id);
}
