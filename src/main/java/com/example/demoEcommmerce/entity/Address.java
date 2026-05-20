package com.example.demoEcommmerce.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Address {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
private Long id;
private String street;
private String state;
private String city;
private String country;
private String zipCode;

public User getUser() {
	return user;
}
public void setUser(User user) {
	this.user = user;
}


public Address() {
}
public Address(Long id, String street, String state, String city, String country, String zipCode) {
	this.id = id;
	this.street = street;
	this.state = state;
	this.city = city;
	this.country = country;
	this.zipCode = zipCode;
}
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getStreet() {
	return street;
}
public void setStreet(String street) {
	this.street = street;
}
public String getState() {
	return state;
}
public void setState(String state) {
	this.state = state;
}
public String getCity() {
	return city;
}
public void setCity(String city) {
	this.city = city;
}
public String getCountry() {
	return country;
}
public void setCountry(String country) {
	this.country = country;
}
public String getZipCode() {
	return zipCode;
}
public void setZipCode(String zipCode) {
	this.zipCode = zipCode;
}

@OneToOne
@JoinColumn(name = "user_id")
@JsonIgnore
private User user;


}
