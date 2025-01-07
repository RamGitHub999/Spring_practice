package com.ecommerce.ECommerceApp.Controller;

import com.ecommerce.ECommerceApp.Exceptions.ProductNotFoundException;
import com.ecommerce.ECommerceApp.Model.Product;
import com.ecommerce.ECommerceApp.PayLoad.ProductDto;
import com.ecommerce.ECommerceApp.response.ApiResponse;
import com.ecommerce.ECommerceApp.services.ServiceImpl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
    @Autowired
    private ProductServiceImpl prodServices;

    @GetMapping("/product/{id}/productById")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id){
        try {
            Product prod=prodServices.getProductById(id);
            return  ResponseEntity.ok(new ApiResponse("Product Founded",prod));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),NOT_FOUND));
        }
        catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).
                    body(new ApiResponse(e.getMessage(),INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/addProduct")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductDto prodDto)
    {
        try {
            Product theProduct= prodServices.createProduct(prodDto);
            return ResponseEntity.ok(new ApiResponse("Product Created",theProduct));
        } catch (Exception e) {
            //throw new RuntimeException(e);
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).
                    body(new ApiResponse(e.getMessage(),INTERNAL_SERVER_ERROR));
        }
    }

    @PutMapping("/product/updateProduct")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductDto prodDto,@RequestParam Long id)
    {
        try {
            Product theProduct= prodServices.updateProduct(prodDto,id);
            return ResponseEntity.ok(new ApiResponse("Product Updated",theProduct));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),NOT_FOUND));
        }
        catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).
                    body(new ApiResponse(e.getMessage(),INTERNAL_SERVER_ERROR));
        }

    }
    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<ApiResponse> deleteProductById(@PathVariable Long id)
    {
        try {
            prodServices.deleteProduct(id);
            return ResponseEntity.ok(new ApiResponse("Product deleted!",null));
        } catch (ProductNotFoundException e) {
            //throw new RuntimeException(e);
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),NOT_FOUND));
        }
        catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).
                    body(new ApiResponse(e.getMessage(),INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getProductAll(){

        try {
            List<Product> products=prodServices.getAllProducts();
            List<ProductDto> productDtoList=prodServices.productDtoList(products);
            if(productDtoList.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Products Not Found",productDtoList));
            }
            return ResponseEntity.ok(new ApiResponse("The Products",productDtoList));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).
                    body(new ApiResponse(e.getMessage(),INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/by/get-brand-name")
    public ResponseEntity<ApiResponse> getProductByBandAndName(@RequestParam String brand,@RequestParam String name){

        try {
            List<Product> products=prodServices.getProductByBrandAndName(brand,name);
            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Products Not Found",null));
            }
            return ResponseEntity.ok(new ApiResponse("The Products",products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).
                    body(new ApiResponse(e.getMessage(),INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/by/get-category-brand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String category,@RequestParam String brand){

        try {
            List<Product> products=prodServices.productsByCategoryAndBrand(category,brand);
            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Products Not Found",products));
            }
            return ResponseEntity.ok(new ApiResponse("The Products",products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).
                    body(new ApiResponse(e.getMessage(),INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/by/name")
    public ResponseEntity<ApiResponse> getProductByName(@RequestParam String name){

        try {
            List<Product> products=prodServices.getProductByName(name);
            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Products Not Found",products));
            }
            return ResponseEntity.ok(new ApiResponse("The Products",products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).
                    body(new ApiResponse(e.getMessage(),INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/by/brand")
    public ResponseEntity<ApiResponse> getProductByBrand(@RequestParam String brand){

        try {
            List<Product> products=prodServices.getProductByBrand(brand);
            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Products Not Found",null));
            }
            return ResponseEntity.ok(new ApiResponse("The Products",products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).
                    body(new ApiResponse(e.getMessage(),INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/by/category")
    public ResponseEntity<ApiResponse> getProductByCategory(@RequestParam String category){

        try {
            List<Product> products=prodServices.productsByCategory(category);
            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Products Not Found",products));
            }
            return ResponseEntity.ok(new ApiResponse("The Products",products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).
                    body(new ApiResponse(e.getMessage(),INTERNAL_SERVER_ERROR));
        }
    }
}
