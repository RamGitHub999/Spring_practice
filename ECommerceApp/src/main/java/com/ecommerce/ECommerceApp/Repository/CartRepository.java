package com.ecommerce.ECommerceApp.Repository;

import com.ecommerce.ECommerceApp.Model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    Cart findByUserId(Long userId);
}
