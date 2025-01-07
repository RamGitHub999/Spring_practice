package com.ecommerce.ECommerceApp.Repository;

import com.ecommerce.ECommerceApp.Model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {

    //List<Image> findByProductId(Long imageId);

    Image getImageById(Long imageId);
    List<Image> getImageByProductId(Long productId);
}
