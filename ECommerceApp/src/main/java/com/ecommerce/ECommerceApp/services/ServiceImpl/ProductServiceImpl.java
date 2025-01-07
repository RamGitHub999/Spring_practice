package com.ecommerce.ECommerceApp.services.ServiceImpl;

import com.ecommerce.ECommerceApp.Exceptions.ProductNotFoundException;
import com.ecommerce.ECommerceApp.Model.Category;
import com.ecommerce.ECommerceApp.Model.Image;
import com.ecommerce.ECommerceApp.Model.Product;
import com.ecommerce.ECommerceApp.PayLoad.ImageDto;
import com.ecommerce.ECommerceApp.PayLoad.ProductDto;
import com.ecommerce.ECommerceApp.Repository.CategoryRepository;
import com.ecommerce.ECommerceApp.Repository.ImageRepository;
import com.ecommerce.ECommerceApp.Repository.ProductRepository;
import com.ecommerce.ECommerceApp.services.IProductService;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private  ProductRepository productRepoService;
    @Autowired
    private  CategoryRepository categoryRepository;

    @Autowired
    private ImageRepository imageRepo;

    @Autowired
    private ModelMapper modelMapper;



    @Override
    public Product createProduct(@NotNull ProductDto product) {
        Category category=Optional.ofNullable(categoryRepository.findByName(product.getCategory().getName()))
                .orElseGet(()->{
                    Category newCategory=new Category(product.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        product.setCategory(category);

        return productRepoService.save(addProduct(product,category));
    }
    private Product addProduct(ProductDto product, Category category)
    {
        return new Product(
                product.getName(),
                product.getBrand(),
                product.getInventory(),
                product.getPrice(),
                product.getDescription(),
                category
        );
    }

    @Override
    public Product getProductById(Long productId) {
        return productRepoService.findById(productId)
                .orElseThrow(()->new ProductNotFoundException("Product Not Found"));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepoService.findAll();
    }

    @Override
    public Product updateProduct(ProductDto product, Long productId) {

       return productRepoService.findById(productId)
               .map(existingProduct->updateExistingProduct(existingProduct,product))
               .map(productRepoService :: save)
               .orElseThrow(()->new ProductNotFoundException("Product Not Found"));

    }

    private  Product updateExistingProduct(Product existingProduct,ProductDto prodDto){

        existingProduct.setName(prodDto.getName());
        existingProduct.setBrand(prodDto.getBrand());
        existingProduct.setPrice(prodDto.getPrice());
        existingProduct.setInventory(prodDto.getInventory());
        existingProduct.setDescription(prodDto.getDescription());
        Category category=categoryRepository.findByName(prodDto.getCategory().getName());
        existingProduct.setCategory(category);

        return productRepoService.save(existingProduct);

    }

    @Override
    public void deleteProduct(Long productId) {
        productRepoService.findById(productId).ifPresentOrElse(productRepoService::delete
        ,()->{throw new ProductNotFoundException("Product Not Found");
        });
    }

    @Override
    public List<Product> productsByCategory(String category) {
        return productRepoService.findByCategoryName(category);
    }

    @Override
    public List<Product> productsByCategoryAndBrand(String category, String brand) {
        return productRepoService.findByCategoryNameAndBrand(category,brand);
    }

    @Override
    public List<Product> getProductByBrand(String brand) {
        return productRepoService.findByBrand(brand);
    }

    @Override
    public List<Product> getProductByName(String name) {
        return productRepoService.findByName(name);
    }

    @Override
    public List<Product> getProductByBrandAndName(String brand, String name) {
        return productRepoService.findByBrandAndName(brand,name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepoService.countByBrandAndName(brand,name);
    }

    @Override
    public List<ProductDto> productDtoList(List<Product> products){
        return products.stream().map(this::convertToDto).toList();
    }

    @Override
    public ProductDto convertToDto(Product product){
        ProductDto productDto=modelMapper.map(product,ProductDto.class);
        List<Image> images=imageRepo.getImageByProductId(product.getId());
        images.forEach(image->System.out.println(image.getDownloadUrl()));
        List<ImageDto> imageDto=images.stream().map(image->modelMapper.map(image,ImageDto.class)).toList();
        productDto.setImages(imageDto);
        return productDto;
    }
}
