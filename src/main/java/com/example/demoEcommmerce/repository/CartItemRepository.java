package com.example.demoEcommmerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demoEcommmerce.entity.CartItem;
import com.example.demoEcommmerce.entity.Product;
import com.example.demoEcommmerce.entity.User;

public interface CartItemRepository extends JpaRepository<CartItem,Long>{


    CartItem findByUserAndProduct(
            User user,
            Product product
    );

    void deleteByUserAndProduct(
            User user,
            Product product
    );

    List<CartItem> findByUser(User user);

    void deleteByUser(User user);


}
