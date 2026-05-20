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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demoEcommmerce.dto.ProductRequest;
import com.example.demoEcommmerce.dto.ProductResponse;
import com.example.demoEcommmerce.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	private ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	@PostMapping
	public ResponseEntity<ProductResponse> createProduct(
			@Valid @RequestBody ProductRequest productRequest){
		return new ResponseEntity<ProductResponse>(productService.createProduct(productRequest),HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<ProductResponse>> getProducts(){
		return ResponseEntity.ok(productService.getAllProducts());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ProductResponse> updateProduct(
			@PathVariable Long id, @Valid @RequestBody ProductRequest productRequest){
		return productService.updateProduct(id,productRequest)
				.map(ResponseEntity :: ok)
				.orElseGet(()-> ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
		boolean deleted = productService.deleteProduct(id);
		return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<ProductResponse>> searchProducts(@RequestParam String keyword){
		return ResponseEntity.ok(productService.searchProduct(keyword));
	}
}
