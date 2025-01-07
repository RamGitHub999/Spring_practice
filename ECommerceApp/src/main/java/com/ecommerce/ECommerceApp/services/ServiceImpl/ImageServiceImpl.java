package com.ecommerce.ECommerceApp.services.ServiceImpl;

import com.ecommerce.ECommerceApp.Exceptions.ResourceNotFoundException;
import com.ecommerce.ECommerceApp.Model.Image;
import com.ecommerce.ECommerceApp.Model.Product;
import com.ecommerce.ECommerceApp.PayLoad.ImageDto;
import com.ecommerce.ECommerceApp.Repository.ImageRepository;
import com.ecommerce.ECommerceApp.services.IImageService;
import com.ecommerce.ECommerceApp.services.IProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageServiceImpl implements IImageService {

    @Autowired
    private ImageRepository imageRepo;

    @Autowired
    private IProductService prodService;

    @Autowired
    private ModelMapper ModelMapper;

    @Override
    public Image  getImageById(Long imageId) {
        System.out.println("+++++++++++++++++ In getImageBy Id"+imageId);
       // return imageRepo.getImageById(imageId);

        try {
            return imageRepo.getImageById(imageId);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public void deleteImageById(Long imageId) {
        imageRepo.findById(imageId).ifPresentOrElse(imageRepo::delete,()->
        {
            throw new ResourceNotFoundException("Image Not Found By that Id " +imageId);
        });
    }

    @Override
    public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {
        Product product=prodService.getProductById(productId);
        List<ImageDto> imagesSaved=new ArrayList<>();

            for(MultipartFile file :files){
                try {
                    Image img=new Image();
                    img.setImage(new SerialBlob(file.getBytes()));
                    img.setFileType(file.getContentType());
                    img.setFileName(file.getOriginalFilename());
                    img.setProduct(product);

                    String downloadUrl="/api/v1/images/image/download/"+img.getId();
                    img.setDownloadUrl(downloadUrl);
                    Image savedImage=imageRepo.save(img);
                    savedImage.setDownloadUrl("/api/v1/images/image/download/"+savedImage.getId());
                    imageRepo.save(savedImage);

                    ImageDto img2=new ImageDto();
                    img2.setImageId(savedImage.getId());
                    img2.setImageName(savedImage.getFileName());
                    img2.setDownloadUrl(savedImage.getDownloadUrl());
                    imagesSaved.add(img2);

                } catch (IOException |SQLException e) {
                    throw new RuntimeException(e);
                }
            }

        return imagesSaved;
    }

    @Override
    public Image  updateImage(MultipartFile file, Long imageId)
    {
        System.out.println("++++++++ImplService+++++++++"+imageId);
        Image image=getImageById(imageId);
        Image updatedImage=null;
        try {

            if(image !=null) {
                System.out.println("image"+file.getOriginalFilename());
                image.setFileName(file.getOriginalFilename());
                image.setImage(new SerialBlob(file.getBytes()));
                 updatedImage=imageRepo.save(image);

            }
        }
        catch (IOException| SQLException e)
        {
            throw new RuntimeException(e.getMessage());
        }
        return updatedImage;

    }

    @Override
    public ImageDto convertToDto(Image image){
        ImageDto imageDto= ModelMapper.map(image,ImageDto.class);
        return imageDto;
    }
}
