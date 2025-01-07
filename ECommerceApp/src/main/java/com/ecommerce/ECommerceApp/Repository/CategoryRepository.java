package com.ecommerce.ECommerceApp.Repository;

import com.ecommerce.ECommerceApp.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findByName(String name);

    boolean existsByName(String name);

    List<Category> getCategoryByName(String name);

    // boolean existByName(String name);
}
