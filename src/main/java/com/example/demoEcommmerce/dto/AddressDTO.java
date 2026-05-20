package com.example.demoEcommmerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class AddressDTO {
	 @NotBlank(message = "Street name is required")
	private String street;
	 @NotBlank(message = "State is required")
	private String state;
	 @NotBlank(message = "City is required")
	private String city;
	 @NotBlank(message = "Country is required")
	private String country;
	 @Pattern(regexp = "^[0-9]{5}(?:-[0-9]{4})?$", message = "Invalid ZIP code format")
	private String zipCode;

	
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
	public AddressDTO(String street, String state, String city, String country, String zipCode) {
		this.street = street;
		this.state = state;
		this.city = city;
		this.country = country;
		this.zipCode = zipCode;
	}
	public AddressDTO() {
		// TODO Auto-generated constructor stub
	}
	
	

}
