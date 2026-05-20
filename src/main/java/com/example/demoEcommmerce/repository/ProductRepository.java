package com.example.demoEcommmerce.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.demoEcommmerce.entity.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {

	List<Product> findByActiveTrue();
	
	 @Query("SELECT p FROM Product p WHERE p.active = true AND p.stock > 0 AND LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
		List<Product> searchProducts(@Param("keyword") String keyword);
		
	 List<Product> findByNameContainingIgnoreCase(String keyword);

}
