package com.example.demoEcommmerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demoEcommmerce.entity.Order;
import com.example.demoEcommmerce.entity.User;

public interface OrderRepository extends JpaRepository<Order, Long> {
	List<Order> findByUser(User user);
}
