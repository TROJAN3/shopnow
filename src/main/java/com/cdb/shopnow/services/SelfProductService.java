package com.cdb.shopnow.services;


import com.cdb.shopnow.exceptions.ProductNotExistsException;
import com.cdb.shopnow.models.Product;
import com.cdb.shopnow.repositories.CategoryRepository;
import com.cdb.shopnow.repositories.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("selfProductService")
public class SelfProductService implements ProductService{

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    public SelfProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }
    @Override
    public Product getSingleProduct(Long id) throws ProductNotExistsException {

        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()){
            throw new ProductNotExistsException("Product with id: " + id + " doesn't exist.");
        }
        return (productOptional.get());
    }

    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteProduct(Long id) {
        return null;
    }

    @Override
    public Product[] getAllProducts() {
        return new Product[0];
    }

    @Override
    public Product addnewProduct(Product product) {
        return null;
    }
}
