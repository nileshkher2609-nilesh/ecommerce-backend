package com.example.demoEcommmerce.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demoEcommmerce.dto.OrderResponse;
import com.example.demoEcommmerce.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	 
	private OrderService orderService;
	

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}


	@PostMapping
	public ResponseEntity<OrderResponse> createOrder(
			Authentication authentication ){
		 String email = authentication.getName();
	Optional<OrderResponse> order = orderService.createOrder(email);
	return order
			.map(response -> new ResponseEntity<>(response,HttpStatus.CREATED))
			.orElseGet(() -> ResponseEntity.badRequest().build()); 
	}
	
	@GetMapping
	public ResponseEntity<List<OrderResponse>> getOrders(
			Authentication authentication){
		String email = authentication.getName();
		
		return ResponseEntity.ok(
				orderService.getOrders(email));
	}
}
