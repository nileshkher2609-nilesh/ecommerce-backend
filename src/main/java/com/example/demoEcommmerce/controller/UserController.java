package com.example.demoEcommmerce.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demoEcommmerce.dto.UserRequest;
import com.example.demoEcommmerce.dto.UserResponse;
import com.example.demoEcommmerce.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

 private UserService userService;
	
	public UserController(UserService userService) {
	this.userService = userService;
}

	@GetMapping
	public ResponseEntity<List<UserResponse>> getAllUser(){
		return new ResponseEntity<>(userService.fetchAllUser(),HttpStatus.OK);
	}
	
	@PostMapping
		public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest){
			UserResponse response = userService.addUser(userRequest);
			return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
		
		return userService.fetchUserById(id)
				.map(ResponseEntity::ok)
				.orElseGet(()->ResponseEntity.notFound().build());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> updateUserbyId(@PathVariable Long id, @Valid @RequestBody UserRequest updateUserRequest){
		userService.updateUser(id, updateUserRequest);
		return ResponseEntity.ok("User Has Been Updated");
	}
	@DeleteMapping("/{id}")
     public ResponseEntity<String> deleteUser(@PathVariable Long id){
		boolean userDeleted = userService.deleteUser(id);
		
		if (userDeleted) {
			return ResponseEntity.ok("User Deleted Successfully");
		}
		
		return ResponseEntity.notFound().build();
	}
}
