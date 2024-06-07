package com.mateo.crudapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mateo.crudapi.models.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

}
