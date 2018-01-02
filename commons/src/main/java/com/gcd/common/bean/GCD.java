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


/**
 * Model for the GCD table.
 * @author Vinod
 *
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class GCD implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="userkey")
	private String userkey;

	@NotNull
	@Digits(fraction = 0, integer = 12)
	@Column(name = "gcd")
	private int gcd;

	@JsonCreator
	public GCD(@JsonProperty("id") Long id, @JsonProperty("gcd") int gcd) {
		this.id = id;
		this.gcd = gcd;
	}
	
	public GCD(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getGcd() {
		return gcd;
	}

	public void setGcd(int gcd) {
		this.gcd = gcd;
	}
	
	public String getUserKey() {
		return userkey;
	}

	public void setKey(String userkey) {
		this.userkey = userkey;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(" ID " + getId());
		str.append(" GCD " + getGcd());
		str.append(" userkey " + getUserKey());
		return str.toString();
	}
}
