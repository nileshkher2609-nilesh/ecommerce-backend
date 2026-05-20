package com.example.demoEcommmerce.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.example.demoEcommmerce.dto.CartItemRequest;
import com.example.demoEcommmerce.entity.CartItem;
import com.example.demoEcommmerce.entity.Product;
import com.example.demoEcommmerce.entity.User;
import com.example.demoEcommmerce.repository.CartItemRepository;
import com.example.demoEcommmerce.repository.ProductRepository;
import com.example.demoEcommmerce.repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CartItemService {

	private ProductRepository productRepository;
	private CartItemRepository cartItemRepository;
	private UserRepository userRepository;
	
	
	
	public CartItemService(ProductRepository productRepository, CartItemRepository cartItemRepository,
			UserRepository userRepository) {
		this.productRepository = productRepository;
		this.cartItemRepository = cartItemRepository;
		this.userRepository = userRepository;
	}



	public boolean addToCart(String email, CartItemRequest request) {
		
		// Now we need to check whether the product and user exist or not, for that we user optional and .get() to extract the value.
		Optional<Product> productOpt = productRepository.findById(request.getProductId());
		
		if(productOpt.isEmpty()) {
			return false;
		}
		Product product = productOpt.get();
		if(product.getStock() < request.getQuantity()) {
			return false;
		}
		
		Optional<User> userOpt = userRepository.findByEmail(email);
		
		if(userOpt.isEmpty()) {
			return false;
		}
		User user = userOpt.get();
		
		CartItem existingCartItem = cartItemRepository.findByUserAndProduct(user,product);
		if(existingCartItem != null) {
//if the item already exist then update the quantity and then multiply it by the new quantity.
			existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
			existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
			cartItemRepository.save(existingCartItem);
		}
		else {
			CartItem cartItem = new CartItem();
			cartItem.setUser(user);
			cartItem.setProduct(product);
			cartItem.setQuantity(request.getQuantity());
			cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
			cartItemRepository.save(cartItem);
		}
		return true;
	}
	
	public boolean updateCartQuantity(Long cartItemId, int quantity) {
		Optional<CartItem> cartItemOpt = cartItemRepository.findById(cartItemId);
		
		if(cartItemOpt.isEmpty()) {
			return false;
		}
		// Remove item if quantity becomes 0
		CartItem cartItem = cartItemOpt.get();
	    if(quantity <= 0) {
	        cartItemRepository.delete(cartItem);
	        return true;
	    }
	    
		cartItem.setQuantity(quantity);
		
		cartItem.setPrice(
				cartItem.getProduct().getPrice()
				.multiply(BigDecimal.valueOf(quantity))
				);
		return true;
	}



	public boolean deleteItemFromCart(String email, Long productId) {
		// TODO Auto-generated method stub
		 User user = userRepository.findByEmail(email)
		            .orElseThrow(() ->
		                    new RuntimeException("User not found"));

		    Product product = productRepository.findById(productId)
		            .orElseThrow(() ->
		                    new RuntimeException("Product not found"));

		    CartItem cartItem =
		            cartItemRepository.findByUserAndProduct(user, product);

		    if (cartItem != null) {

		        cartItemRepository.delete(cartItem);

		        return true;
		    }

		    return false;
		    
		    // we are using this because repository deals with entity not string so we cannot pass string.
	}



	public List<CartItem> getCart(String email) {
		  User user = userRepository.findByEmail(email)
		            .orElseThrow(() ->
		                    new RuntimeException("User not found"));

		    return cartItemRepository.findByUser(user);
	}



	public void clearCart(String email) {

	    User user = userRepository.findByEmail(email)
	            .orElseThrow(() ->
	                    new RuntimeException("User not found"));

	    cartItemRepository.deleteByUser(user);
	}
	
	

}
