package com.example.firstsample.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class Topic {
	@Id
	@NotEmpty(message ="Topic id cannnot be null or empty")
	@NotBlank(message ="Topic id cannnot be blank")
	@Pattern(message = "id must be a number", regexp="^[0-9]*$")
	private String id;
	@Column
	@NotNull(message ="Topic name cannnot be null")
	@Size(min=5, max=6, message = "Topic name size be between 5 chars to 6 chars long.")
	private String name;
	@Column
	@NotNull(message ="Topic desc cannnot be null")
	@Size(max=15, message ="Description can be of 15 characters long max")
	private String description;
	
	@Column
	private Date date;
	
	@Column
	private Integer number;

	public Topic() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Topic(String id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}

}
