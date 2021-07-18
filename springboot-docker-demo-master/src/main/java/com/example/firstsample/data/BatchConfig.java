package com.example.firstsample.data;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;

import com.example.firstsample.domain.Topic;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
	@EnableBatchProcessing
	public class BatchConfig {

	  @Autowired
	  public JobBuilderFactory jobBuilderFactory;

	  @Autowired
	  public StepBuilderFactory stepBuilderFactory;
	  
	  private final String[] FIELD_NAMES = {"id","name","description", "date", "number"};

	  //reads data from file to input
//	  @Bean
//	  public FlatFileItemReader<TopicInput> csvReader() {
//	    return new FlatFileItemReaderBuilder<TopicInput>()
//	      .name("TopicItemReader")
//	      .resource(new ClassPathResource("data.csv"))
//	      .delimited()
//	      .names(FIELD_NAMES)
//	      .fieldSetMapper(new BeanWrapperFieldSetMapper<TopicInput>() {{
//	        setTargetType(TopicInput.class);
//	      }})
//	      .build();
//	  }

	  @Bean
	  @StepScope
	  public JsonItemReader<TopicInput> myJsonReader() {
	      final ObjectMapper mapper = new ObjectMapper();
	      final JacksonJsonObjectReader<TopicInput> jsonObjectReader = new JacksonJsonObjectReader<>(
	    		  TopicInput.class);
	      jsonObjectReader.setMapper(mapper);
	      return new JsonItemReaderBuilder<TopicInput>().jsonObjectReader(jsonObjectReader)
	              .resource(new ClassPathResource("data.json"))
	              .name("myReader")
	              .build();
	  }

	  @Bean
	  public TopicDataProcessor processor() {
	    return new TopicDataProcessor();
	  }

	  //writes data to db datasource
	  @Bean
	  public JdbcBatchItemWriter<Topic> writer(DataSource dataSource) {
	    return new JdbcBatchItemWriterBuilder<Topic>()
	      .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
	      .sql("INSERT INTO TOPIC (id, name, description, date, number) VALUES (:id, :name, :description, :date, :number)")
	      .dataSource(dataSource)
	      .build();
	  }
	  //Creates job which listens and have step 1
	  @Bean
	  public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
	    return jobBuilderFactory.get("importUserJob")
	      .incrementer(new RunIdIncrementer())
	      .listener(listener)
	      .flow(step1)
	      .end()
	      .build();
	  }

	  //each step has reader, processor and writer to finish the job
	  @Bean
	  public Step step1(JdbcBatchItemWriter<Topic> writer) {
	    return stepBuilderFactory.get("step1")
	      .<TopicInput, Topic> chunk(10)
	      .reader(myJsonReader()) //change here with csv reader later
	      .processor(processor())
	      .writer(writer)
	      .build();
	  }
}
