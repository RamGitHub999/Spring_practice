package com.ecommerce.ECommerceApp.Repository;

import com.ecommerce.ECommerceApp.Model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders,Long> {

    List<Orders> findByUserId(Long userId);
}
