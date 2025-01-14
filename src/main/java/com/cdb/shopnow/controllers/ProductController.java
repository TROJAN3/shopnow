package com.cdb.shopnow.controllers;

import com.cdb.shopnow.commons.AuthenticationCommons;
import com.cdb.shopnow.exceptions.ProductNotExistsException;
import com.cdb.shopnow.models.Product;
import com.cdb.shopnow.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;
    private RestTemplate restTemplate;
    private AuthenticationCommons authenticationCommons;
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired

    public ProductController(@Qualifier("selfProductService") ProductService productService,RestTemplate restTemplate,AuthenticationCommons authenticationCommons, RedisTemplate<String,Object> redisTemplate) {
        this.restTemplate = restTemplate;
        this.productService = productService;
        this.authenticationCommons=authenticationCommons;
        this.redisTemplate=redisTemplate;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) throws ProductNotExistsException {

        Product rP = (Product) redisTemplate.opsForHash().get("PRODUCTS","PRODUCT_"+id);

        if(rP != null){
            return new ResponseEntity<>(rP,HttpStatus.OK);
        }

        Product product = productService. getSingleProduct(id);

        redisTemplate.opsForHash().put("PRODUCTS","PRODUCT_"+id,product);


        return new ResponseEntity<>(
                product, HttpStatus.OK
        );
    }

    @GetMapping("/")
    public ResponseEntity<List<Product>> getAllProducts(){

        Product[] products = productService.getAllProducts();
        List<Product> productResponse = new ArrayList<Product>();
        for (Product p : products){
            productResponse.add(p);
        }

        ResponseEntity<List<Product>> resp = new ResponseEntity<>(productResponse,HttpStatus.OK);
         return resp;
    }

    @GetMapping
    public List<Product> getProductsByCategory(@RequestParam String category){
        productService.getAllProducts();
        return new ArrayList<>();
    }

    @PostMapping()
    public Product addProduct(@RequestBody Product product){
        return productService.addProduct(product);
    }

    @PatchMapping("/{id}")
    public Product updateProduct(@PathVariable("id") Long id, @RequestBody Product product){
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") Long id){
        productService.deleteProduct(id);
    }
}
