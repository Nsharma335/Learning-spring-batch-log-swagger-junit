package com.example.firstsample.web;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.example.firstsample.WebSecurityConfig;
import com.example.firstsample.domain.AuthenticationRequest;
import com.example.firstsample.domain.AuthenticationResponse;
import com.example.firstsample.domain.Topic;
import com.example.firstsample.exception.ErrorDetails;
import com.example.firstsample.exception.ResourceNotFoundException;
import com.example.firstsample.service.MyUserDetailsService;
import com.example.firstsample.service.TopicService;
import com.example.firstsample.util.JwtUtil;

@RestController
@RequestMapping("/topics")
public class TopicController {

	private static final Logger LOGEER = LoggerFactory.getLogger(TopicController.class);

	@Autowired
	private TopicService topicservice;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private MyUserDetailsService userDetailsService;

	@PostMapping
	public void createTopic(@Valid @RequestBody Topic topic) {
		LOGEER.info("POST /topics ", topic.getId());
		topicservice.addTopic(topic); // first verify if it doen't exist then only create
		//return new ResponseEntity<Topic>(topic, HttpStatus.OK);
	}
	@GetMapping("/{id}")
	public Topic getTopicbyId(@PathVariable("id") String id) {
		LOGEER.info("GET /topics/{} "+ id);
		return topicservice.getTopic(id).orElseThrow(() -> new ResourceNotFoundException("Topic does not exist: " + id));
	}
	
	@GetMapping
	public List<Topic> getAllTopic() {
		LOGEER.info("GET /topics ");
		return topicservice.getAllTopics();
	}

	
	//ResponseEntity : is used to set the body, status and headers of http response, it used to do the fine tuning of resposne.
	//ResponseEntity represents the whole HTTP response: status code, headers, and body. As a result, we can use it to fully configure the HTTP response.
	//ResponseEntity is a generic type. Consequently, we can use any type as the response body:
	@PutMapping("/{id}")
	public ResponseEntity<?> updateTopic(@PathVariable String id, @Valid @RequestBody Topic newTopic) {
		LOGEER.info("PUT /topics/{} "+ id);
//		topicservice.getTopic(id).flatMap(topic -> {
//			        topic.setName(newTopic.getName());
//			        topic.setDescription(newTopic.getDescription());
//			        topicservice.addTopic(topic);
//			      })
//			      .orElseGet(() -> {
//			    	 newTopic.setId(id);
//			         topicservice.save(newTopic);
//			      });
		Topic topic = topicservice.getTopic(id).orElseThrow(() -> new ResourceNotFoundException("Topic does not exist: " + id));
		topic.setDescription(newTopic.getDescription());
		topic.setName(newTopic.getName());
		topicservice.updateTopic(id, topic);
		
		return new ResponseEntity("Updated succesfully " , HttpStatus.OK );
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteTopic(@PathVariable("id") String id) {
		LOGEER.info("DELETE /topics/{} "+ id);
		Topic topic = topicservice.getTopic(id).orElseThrow(() -> new ResourceNotFoundException("Topic does not exist: " + id));
		topicservice.deleteTopic(id);
		return ResponseEntity.ok("Topic with id "+ id +" deleted successfully.");
	}

	
	@RequestMapping({ "/hello" })
	public String firstPage() {
		return "Hello World";
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		final String jwt = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}

}
