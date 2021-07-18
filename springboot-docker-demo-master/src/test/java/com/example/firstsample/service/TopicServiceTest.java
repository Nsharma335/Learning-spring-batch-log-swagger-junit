package com.example.firstsample.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
//import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.firstsample.domain.Topic;
import com.example.firstsample.repo.TopicRepository;

@SpringBootTest
public class TopicServiceTest {
	@Autowired 
	private TopicService topicService;
	
	@MockBean
	private TopicRepository topicRepository;
	
	@Test
	public void getTopicsTest(){
		when(topicRepository.findAll()).thenReturn(Stream.of(new Topic("1","java","test1"))
				.collect(Collectors.toList()));
		assertEquals(1, topicService.getAllTopics().size());
		
	}
	@Test
	public void addTopicTest() {
		Topic topic = new Topic("12", "java", "test2");
		topicService.addTopic(topic);
		verify(topicRepository , times(1)).save(topic);
	}
	
	@Test
	public void getTopicByIdTest() {
		String topicId = "12";
		when(topicRepository.findById(topicId))
		.thenReturn(Optional.of(new Topic("12","java","test1")));
		assertEquals("12", topicService.getTopic(topicId).get().getId());
		assertEquals("java", topicService.getTopic(topicId).get().getName());
		assertEquals("test1", topicService.getTopic(topicId).get().getDescription());
	}
	
	@Test
	public void deleteTopicTest() {
		Topic topic = new Topic("12", "java", "test2");
		topicService.deleteTopic(topic.getId());
		verify(topicRepository , times(1)).deleteById(topic.getId());
	}
}

