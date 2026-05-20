package com.example.demoEcommmerce.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class OrderItemDTO {
	@NotNull(message = "ID is required")
	private Long id;
	
	@NotNull(message = "Product ID is required")
	private Long productId;
	
	@NotNull(message = "Price is required")
	@Positive(message = "Price must be greater than 0")
	private BigDecimal price;
	
	 @NotNull(message = "Quantity is required")
	 @Positive(message = "Quantity must be greater than 0")
	private Integer quantity;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public OrderItemDTO(Long id, Long productId, BigDecimal price, Integer quantity) {
		this.id = id;
		this.productId = productId;
		this.price = price;
		this.quantity = quantity;
	}
	
}
