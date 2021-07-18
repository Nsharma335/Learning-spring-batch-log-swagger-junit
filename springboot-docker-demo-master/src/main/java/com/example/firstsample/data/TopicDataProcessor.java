package com.example.firstsample.data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

import com.example.firstsample.domain.Topic;

public class TopicDataProcessor implements ItemProcessor<TopicInput, Topic> {

  private static final Logger log = LoggerFactory.getLogger(TopicDataProcessor.class);

  @Override
  public Topic process(final TopicInput topicInput) throws Exception {
    
	Topic transformedTopic = new Topic();
	transformedTopic.setId(topicInput.getId());
	transformedTopic.setName(topicInput.getName());
	transformedTopic.setDescription(topicInput.getDescription());
	transformedTopic.setNumber(Integer.parseInt(topicInput.getNumber()));
	DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
	Date date = formatter.parse(topicInput.getDate());
	transformedTopic.setDate(date);
    log.info("Converting (" + topicInput + ") into (" + transformedTopic + ")");
    return transformedTopic;
  }

}


