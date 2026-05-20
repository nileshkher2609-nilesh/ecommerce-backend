package com.example.demoEcommmerce.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.example.demoEcommmerce.roles.OrderStatus;

public class OrderResponse {

	private Long id;
	private BigDecimal totalAmount;
	private OrderStatus orderStatus;
	private List<OrderItemDTO> items;
	private LocalDateTime createdAt;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	public List<OrderItemDTO> getItems() {
		return items;
	}
	public void setItems(List<OrderItemDTO> items) {
		this.items = items;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	public OrderResponse(Long id, BigDecimal totalAmount, OrderStatus orderStatus, List<OrderItemDTO> items,
			LocalDateTime createdAt) {
		this.id = id;
		this.totalAmount = totalAmount;
		this.orderStatus = orderStatus;
		this.items = items;
		this.createdAt = createdAt;
	}
	
}
