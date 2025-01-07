package com.ecommerce.ECommerceApp.Repository;

import com.ecommerce.ECommerceApp.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface UserRepository extends JpaRepository<User,Long> {
     boolean existsByEmail(String email);

    User findByEmail(String username);
}
