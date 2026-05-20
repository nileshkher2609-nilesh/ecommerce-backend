package com.example.demoEcommmerce.dto;

import com.example.demoEcommmerce.roles.UserRole;


public class UserResponse {
	private String id;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private UserRole role;
	private AddressDTO addressdto;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public UserRole getRole() {
		return role;
	}
	public void setRole(UserRole role) {
		this.role = role;
	}
	public AddressDTO getAddressdto() {
		return addressdto;
	}
	public void setAddressdto(AddressDTO addressdto) {
		this.addressdto = addressdto;
	}
	public UserResponse(String id, String firstName, String lastName, String email, String phoneNumber, UserRole role,
			AddressDTO addressdto) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.role = role;
		this.addressdto = addressdto;
	}
	public UserResponse() {
		// TODO Auto-generated constructor stub
	}
	
}
