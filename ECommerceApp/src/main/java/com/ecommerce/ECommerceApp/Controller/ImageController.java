package com.ecommerce.ECommerceApp.Controller;

import com.ecommerce.ECommerceApp.Exceptions.ResourceNotFoundException;
import com.ecommerce.ECommerceApp.Model.Image;
import com.ecommerce.ECommerceApp.PayLoad.ImageDto;
import com.ecommerce.ECommerceApp.Repository.ImageRepository;
import com.ecommerce.ECommerceApp.response.ApiResponse;
import com.ecommerce.ECommerceApp.services.ServiceImpl.ImageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("${api.prefix}/images")
@RequiredArgsConstructor
public class ImageController {

    //Constructor Injection
    private final ImageServiceImpl imageService;

    private final ImageRepository imageRepo;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImage(@RequestParam List<MultipartFile> files,@RequestParam Long productId){
        try {
            List<ImageDto> images= imageService.saveImage(files,productId);
            return  ResponseEntity.ok(new ApiResponse("Upload successFully",images));
        } catch (Exception e) {
           return ResponseEntity.status(INTERNAL_SERVER_ERROR).
                    body(new ApiResponse("upload Failed",e.getMessage()));
        }
    }

    @GetMapping("/image/getImage/{imageId}")
    public ResponseEntity<ApiResponse> getImageById(@PathVariable Long imageId)
    {
        try {
            Image image=imageService.getImageById(imageId);
            ImageDto imageDto=imageService.convertToDto(image);
            if(image !=null){
                //System.out.println("system founded or not"+image);
                return  ResponseEntity.ok(new ApiResponse("image founded",imageDto));
            }
            return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse("image NOT founded",NOT_FOUND));
        } catch (ResourceNotFoundException e) {
            return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),NOT_FOUND));
        }
    }

    @GetMapping("/image/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) throws SQLException {
        try {
            Image image= imageService.getImageById(imageId) ;
            ByteArrayResource resource=new ByteArrayResource(image.getImage().getBytes(1,(int) image.getImage().length()));
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" +image.getFileName() + "\"")
                    .body(resource);
        } catch (SQLException | ResourceNotFoundException e) {
           // return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Products Not Found",null));
            throw new ResourceNotFoundException(e.getMessage());
        }
        catch (Exception e){
            throw new RuntimeException(String.valueOf(INTERNAL_SERVER_ERROR));
        }
    }

    @PutMapping("/image/update/{imageId}")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId,@RequestBody MultipartFile file){
        try {

            Optional<Image> image1=imageRepo.findById(imageId);
            if(image1.isPresent()){
                System.out.println("+++++++++++++++++ In Controller"+imageId);
                Image image=imageService.getImageById(image1.get().getId());
            }

            if(image1.isPresent()){
                Image image=imageService.updateImage(file,image1.get().getId());
                ImageDto imageDto=imageService.convertToDto(image);
                return ResponseEntity.ok(new ApiResponse("file Updated Successfully!",imageDto));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("update Failed!",INTERNAL_SERVER_ERROR));

    }


    @DeleteMapping("/image/delete/{imageId}")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId){
        try {
            Image image=imageService.getImageById(imageId);
            if(image !=null){
                imageService.deleteImageById(imageId);
                return ResponseEntity.ok(new ApiResponse("file delete Successfully!",null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("delete Failed!",INTERNAL_SERVER_ERROR));

    }

}
