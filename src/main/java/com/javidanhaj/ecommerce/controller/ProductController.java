package com.javidanhaj.ecommerce.controller;

import com.javidanhaj.ecommerce.model.Product;
import com.javidanhaj.ecommerce.service.ProductService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {

        this.service = service;
    }

    @RequestMapping("/")
    public String greets() {

        return "Hello customers!";
    }
    //Listing all products
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
    }
    //Getting a product by id
    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) {

        Product product = service.getProductById(id);

        if (product != null)
            return new ResponseEntity<>(product, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    //Adding a new product
    @PostMapping("product")
    public ResponseEntity<?> addProduct(@RequestPart Product product,
                                        @RequestPart MultipartFile imageFile){
        try {
            Product product1 = service.addProduct(product, imageFile);
            return new ResponseEntity<>(product1, HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    //Getting image by product id(for using image in frontend)
    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId){

        Product product = service.getProductById(productId);
        byte[] imageFile = product.getImageData();

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(product.getImageType()))
                .body(imageFile);

    }

    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestPart Product product, @RequestPart MultipartFile imageFile){
        Product product1 = null;
        try {
            product1 = service.updateProduct(id, product, imageFile);
        } catch (IOException e) {
            return new ResponseEntity<>("Product not found", HttpStatus.BAD_REQUEST);
        }
        if(product1 != null)
            return new ResponseEntity<>("Product updated successfully", HttpStatus.OK);
        else
            return new ResponseEntity<>("Product not found", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){

        Product product =  service.getProductById(id);
        if(product != null) {
            service.deleteProduct(id);
            return new ResponseEntity<>("Product deleted successfuly", HttpStatus.OK);
        } else{
            return new ResponseEntity<>("Product not found!", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword){
        System.out.println("searching with " + keyword);
        List<Product> products = service.searchProducts(keyword);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}