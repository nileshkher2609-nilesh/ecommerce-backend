package com.example.demoEcommmerce.dto;

import java.math.BigDecimal;

public class ProductResponse {
	private Long id;
	private String name;
	private String description;
	private BigDecimal price;
	private Integer stock;
	private String category;
	private String imageUrl;
	private Boolean active;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public ProductResponse(Long id, String name, String description, BigDecimal price, Integer stock, String category,
			String imageUrl, Boolean active) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.stock = stock;
		this.category = category;
		this.imageUrl = imageUrl;
		this.active = active;
	}
	public ProductResponse() {
		// TODO Auto-generated constructor stub
	}
	
}
