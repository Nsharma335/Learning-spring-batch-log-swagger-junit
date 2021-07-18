package com.example.firstsample.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.firstsample.domain.Topic;

public interface TopicRepository extends JpaRepository<Topic, String> {

}
