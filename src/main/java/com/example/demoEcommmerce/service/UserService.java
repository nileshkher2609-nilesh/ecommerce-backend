package com.example.demoEcommmerce.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demoEcommmerce.dto.AddressDTO;
import com.example.demoEcommmerce.dto.UserRequest;
import com.example.demoEcommmerce.dto.UserResponse;
import com.example.demoEcommmerce.entity.Address;
import com.example.demoEcommmerce.entity.User;
import com.example.demoEcommmerce.repository.UserRepository;
import com.example.demoEcommmerce.roles.UserRole;


@Service
public class UserService {

private UserRepository userRepository;
private PasswordEncoder passwordEncoder;




public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
	this.userRepository = userRepository;
	this.passwordEncoder = passwordEncoder;
}
	

	public List<UserResponse> fetchAllUser(){
//		In this we are returning the list of users first it will break into single item and then using map function it will convert to dto by (as mapToUserResponse) is applied on each item.
	return userRepository.findAll()
			.stream()
			.map(this::mapToUserResponse)
			.collect(Collectors.toList());
	}
	


	public UserResponse addUser(UserRequest userRequest){
// The reason why we use updateUserFromRequest is to update user with userRequest value (basically filling up)
		User user = new User();

		updateUserFromRequest(user, userRequest);

		user.setPassword(
		        passwordEncoder.encode(userRequest.getPassword())
		);

		User savedUser = userRepository.save(user);

		return mapToUserResponse(savedUser);
	} 
	
	public Optional<UserResponse> fetchUserById(Long id) {
		return userRepository.findById(id)
				.map(this::mapToUserResponse);
	}
	
	public Boolean updateUser(Long id, UserRequest updatedUserRequest) {
			return userRepository.findById(id)
					.map(existingUser -> {
						updateUserFromRequest(existingUser, updatedUserRequest);
						userRepository.save(existingUser);
						return true;
	            })
	            .orElse(false);
	}
	
	
	public boolean deleteUser(Long id) {
		if(!userRepository.existsById(id)) {
			return false;
		}
		
		return true;
	}
//----------------------------------------------------------------------------
	
	
	private UserResponse mapToUserResponse(User user) {
	    	UserResponse response = new UserResponse();
	    	response.setId(String.valueOf(user.getId()));
	    	response.setFirstName(user.getFirstName());
	    	response.setLastName(user.getLastName());
	    	response.setEmail(user.getEmail());
	    	response.setPhoneNumber(user.getPhoneNumber());
	    	response.setRole(user.getRole());
	    	
	    	if(user.getAddress() != null) {
	    		AddressDTO addressdto = new AddressDTO();
	    		addressdto.setStreet(user.getAddress().getStreet());
	    		addressdto.setCity(user.getAddress().getCity());
	    		addressdto.setState(user.getAddress().getState());
	    		addressdto.setCountry(user.getAddress().getCountry());
	    		addressdto.setZipCode(user.getAddress().getZipCode());
	    		response.setAddressdto(addressdto);
	    	}
	    	
	    	return response;
	    }
	   
	private void updateUserFromRequest(User user, UserRequest userRequest) {

	    user.setFirstName(userRequest.getFirstName());
	    user.setLastName(userRequest.getLastName());
	    user.setEmail(userRequest.getEmail());
	    user.setPhoneNumber(userRequest.getPhoneNumber());
	    if(userRequest.getPassword() != null &&
	    		   !userRequest.getPassword().isBlank()) {

	    		    user.setPassword(
	    		        passwordEncoder.encode(userRequest.getPassword())
	    		    );
	    		}

	    if (user.getRole() == null) {
	        user.setRole(UserRole.CUSTOMER);
	    }

	    Address address = user.getAddress();

	    if (address == null) {
	        address = new Address();
	    }

	    address.setStreet(userRequest.getAddressdto().getStreet());
	    address.setCity(userRequest.getAddressdto().getCity());
	    address.setState(userRequest.getAddressdto().getState());
	    address.setCountry(userRequest.getAddressdto().getCountry());
	    address.setZipCode(userRequest.getAddressdto().getZipCode());

	    address.setUser(user);
	    user.setAddress(address);
	}

	
}
