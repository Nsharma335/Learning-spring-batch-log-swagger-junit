package com.example.firstsample.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.firstsample.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
