package com.example.demoEcommmerce.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Service;

import com.example.demoEcommmerce.dto.OrderResponse;
import com.example.demoEcommmerce.entity.CartItem;
import com.example.demoEcommmerce.entity.Order;
import com.example.demoEcommmerce.entity.OrderItem;
import com.example.demoEcommmerce.entity.Product;
import com.example.demoEcommmerce.entity.User;
import com.example.demoEcommmerce.repository.OrderRepository;
import com.example.demoEcommmerce.repository.ProductRepository;
import com.example.demoEcommmerce.repository.UserRepository;
import com.example.demoEcommmerce.roles.OrderStatus;
import com.example.demoEcommmerce.dto.OrderItemDTO;

@Service
public class OrderService {
	private CartItemService cartItemService;
	private UserRepository userRepository;
	private OrderRepository orderRepository;
	private ProductRepository productRepository;

	
   public OrderService(CartItemService cartItemService, UserRepository userRepository, OrderRepository orderRepository,
			ProductRepository productRepository) {
		this.cartItemService = cartItemService;
		this.userRepository = userRepository;
		this.orderRepository = orderRepository;
		this.productRepository = productRepository;
	}


	public Optional<OrderResponse> createOrder(String email) {
		//validate for cart Items
		
		List<CartItem> cartItems = cartItemService.getCart(email);
		if (cartItems.isEmpty()) {
			return Optional.empty();
		}
		// validate the user
		
		Optional<User> userOptional = userRepository.findByEmail(email);
		if(userOptional.isEmpty()) {
			return Optional.empty();
		}
		User user = userOptional.get();
		
		//calculate total price
		BigDecimal totalPrice = cartItems
				.stream()
				.map(CartItem::getPrice)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		
		
		// Validate stock
		for (CartItem item : cartItems) {

		    Product product = item.getProduct();

		    if (product.getStock() < item.getQuantity()) {

		        throw new RuntimeException(
		                "Insufficient Stock"
		        );
		    }
		}


		// Reduce stock
		for (CartItem item : cartItems) {

		    Product product = item.getProduct();

		    product.setStock(
		            product.getStock() - item.getQuantity()
		    );

		    productRepository.save(product);
		}
		
		//create order
		Order order = new Order();
		order.setUser(user);
		order.setStatus(OrderStatus.CONFIRMED);
		order.setAmount(totalPrice);
		
		List<OrderItem> orderItems = cartItems
				.stream()
				.map(item -> new OrderItem(
						null,
						item.getProduct(),
						item.getQuantity(),
						item.getPrice(), order
						)).toList();
		
		order.setItems(orderItems);
		Order savedOrder = orderRepository.save(order);
		
	
		
		//clear the cart when order is added
		cartItemService.clearCart(email);
		return Optional.of(mapToOrderResponse(savedOrder));
	}
	
	public List<OrderResponse> getOrders(String email){
		User user = userRepository.findByEmail(email)
				.orElseThrow(()-> new RuntimeException("User Not Found"));
		
		List<Order> orders = orderRepository.findByUser(user);
		
		return orders.stream()
				.map(this::mapToOrderResponse)
				.toList();
	}
	
	
//-----------------------Helper Function---------------------------------

	private OrderResponse mapToOrderResponse(Order order) {

	    return new OrderResponse(
	            order.getId(),
	            order.getAmount(),
	            order.getStatus(),
	            order.getItems().stream()
	                    .map(orderItem -> new OrderItemDTO(
	                            orderItem.getId(),
	                            orderItem.getProduct().getId(),
	                            orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity())),
	                            orderItem.getQuantity()
	                    ))
	                    .toList(),
	            order.getCreatedAt()
	    );
	}

}
//1. Validate cart
//2. Validate user
//3. Validate stock
//4. Reduce stock
//5. Create/save order
//6. Clear cart

