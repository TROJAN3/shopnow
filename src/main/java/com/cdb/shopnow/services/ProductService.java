package com.cdb.shopnow.services;

import com.cdb.shopnow.exceptions.ProductNotExistsException;
import com.cdb.shopnow.models.Product;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface ProductService {
    Product getSingleProduct(Long id) throws ProductNotExistsException;
    Product addProduct(Product product);
    Product updateProduct(Long id,Product product);
    ResponseEntity<Void> deleteProduct(Long id);
    Product[] getAllProducts();
    Product addnewProduct(Product product);
}
