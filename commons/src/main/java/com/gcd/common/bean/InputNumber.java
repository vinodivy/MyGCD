package com.gcd.common.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class InputNumber implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="userkey")
	private String userkey;

	@NotNull
	@Digits(fraction = 0, integer = 12)
	@Column(name = "number1")
	private int number1;
	
	@NotNull
	@Digits(fraction = 0, integer = 12)
	@Column(name = "number2")
	private int number2;

	@JsonCreator
	public InputNumber(@JsonProperty("id") Long id, @JsonProperty("number1") int number1,
			@JsonProperty("number2") int number2) {
		this.id = id;
		this.number1 = number1;
		this.number2 = number2;
	}

	public InputNumber() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getNumber1() {
		return number1;
	}

	public void setNumber1(int number1) {
		this.number1 = number1;
	}
	
	public int getNumber2() {
		return number2;
	}

	public void setNumber2(int number2) {
		this.number2 = number2;
	}

	public String getUserkey() {
		return userkey;
	}

	public void setUserkey(String userkey) {
		this.userkey = userkey;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("Generated ID: " + getId());
		str.append(" Number-1: " + getNumber1());
		str.append(" Number-2: " + getNumber2());
		str.append(" key " + getUserkey());
		return str.toString();
	}

}