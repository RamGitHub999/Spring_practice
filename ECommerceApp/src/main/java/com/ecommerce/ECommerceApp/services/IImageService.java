package com.ecommerce.ECommerceApp.services;

import com.ecommerce.ECommerceApp.Model.Image;
import com.ecommerce.ECommerceApp.PayLoad.ImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface IImageService {

    Image getImageById(Long imageId);
    void deleteImageById(Long imageId);
    List<ImageDto> saveImage(List<MultipartFile> file, Long productId);
    Image updateImage(MultipartFile file, Long imageId);

    ImageDto convertToDto(Image image);
}
