package com.example.firstsample.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.firstsample.domain.Topic;
import com.example.firstsample.exception.ResourceNotFoundException;
import com.example.firstsample.repo.TopicRepository;

@Service
public class TopicService {

	@Autowired
	private TopicRepository topicRepository;

	@Autowired
	public TopicService(TopicRepository topicRepository) {
		super();
		this.topicRepository = topicRepository;
	}

	public List<Topic> getAllTopics() {
		return topicRepository.findAll();
	}

	public Optional<Topic> getTopic(String id) {
		return topicRepository.findById(id);
	}

	public void addTopic(Topic topic) {
		topicRepository.save(topic);
	}

	public void updateTopic(String id, Topic topic) {
		topicRepository.save(topic);
	}

	public void deleteTopic(String id) {
		topicRepository.deleteById(id);
	}
}
