package com.example.demoEcommmerce.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.example.demoEcommmerce.dto.ProductRequest;
import com.example.demoEcommmerce.dto.ProductResponse;
import com.example.demoEcommmerce.entity.Product;
import com.example.demoEcommmerce.repository.ProductRepository;

@Service
public class ProductService {
 
	private ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	

	public ProductResponse createProduct(ProductRequest productRequest) {
		Product product = new Product();
		updateProductFromRequest(product,productRequest);
		Product savedProduct = productRepository.save(product);
		return mapToProductResponse(savedProduct);
	}
	
	

	public Optional<ProductResponse> updateProduct(Long id, ProductRequest productRequest){
		return productRepository.findById(id)
				.map(existingProduct -> {
					updateProductFromRequest(existingProduct, productRequest);
					Product savedProduct =productRepository.save(existingProduct);
					return mapToProductResponse(savedProduct);
				});
	}
	
	public List<ProductResponse> getAllProducts() {
		return productRepository.findByActiveTrue()
				.stream()
				.map(this::mapToProductResponse)
				.collect(Collectors.toList());
	}
	
	public boolean deleteProduct(Long id) {

	    return productRepository.findById(id)
	            .map(product -> {
	            	product.setActive(false);
	            	productRepository.save(product);
	            	return true;
	            })
	            .orElse(false);
	}
	
	public List<ProductResponse> searchProduct(String keyword){
		return productRepository.findByNameContainingIgnoreCase(keyword)
				.stream()
				.map(this::mapToProductResponse)
				.collect(Collectors.toList());
	}
	
//--------------------------------Helper Functions------------------------------
	
	private ProductResponse mapToProductResponse(Product savedProduct) {
		ProductResponse response = new ProductResponse();
		response.setId(savedProduct.getId());
		response.setName(savedProduct.getName());
		response.setDescription(savedProduct.getDescription());
		response.setCategory(savedProduct.getCategory());
		response.setPrice(savedProduct.getPrice());
		response.setActive(savedProduct.getActive());
		response.setImageUrl(savedProduct.getImageUrl());
		response.setStock(savedProduct.getStock());
		return response;
	}
	 
	private void updateProductFromRequest(Product product, ProductRequest productRequest) {
		product.setName(productRequest.getName());
		product.setCategory(productRequest.getCategory());
		product.setStock(productRequest.getStock());
		product.setDescription(productRequest.getDescription());
	    product.setPrice(productRequest.getPrice());
	    product.setImageUrl(productRequest.getImageUrl());
	}


	
}
