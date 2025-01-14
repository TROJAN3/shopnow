package com.cdb.shopnow.services;

import com.cdb.shopnow.dtos.FakeStoreDto;
import com.cdb.shopnow.exceptions.ProductNotExistsException;
import com.cdb.shopnow.models.Category;
import com.cdb.shopnow.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("fakeProductService")
public class FakeStoreProductService implements ProductService{

    private final RestTemplate restTemplate ;

    @Autowired
    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate=restTemplate;
    }

    private Product convertFakeStoreProductToProduct (FakeStoreDto fakeStoreDto) {
        Product product = new Product();
        product.setId(fakeStoreDto.getId());
        product.setDescription(fakeStoreDto.getDescription());
        product.setTitle(fakeStoreDto.getTitle());
        product.setPrice(fakeStoreDto.getPrice());
        product.setImageUrl(fakeStoreDto.getImageUrl());
        product.setCategory(new Category());
        product.getCategory().setName(fakeStoreDto.getCategory());

        return product;
    }

    @Override
    public Product getSingleProduct(Long id) throws ProductNotExistsException {
        FakeStoreDto productDto = restTemplate.getForObject("https://fakestoreapi.com/products/"+id,FakeStoreDto.class);

        if (productDto == null) {
            throw new ProductNotExistsException(
                    "Product with id: " + id + " doesn't exist."
            );
        }
        return convertFakeStoreProductToProduct(productDto);
    }


    @Override
    public Product[] getAllProducts() {
        FakeStoreDto[] response = restTemplate.getForObject("https://fakestoreapi.com/products",FakeStoreDto[].class);
        List<Product> answer = new ArrayList<>();


        for (FakeStoreDto dto: response) {
            answer.add(convertFakeStoreProductToProduct(dto));
        }
        return answer.toArray(new Product[0]);
    }

    @Override
    public Product addnewProduct(Product product) {
        return null;
    }

    @Override
    public Product addProduct(Product product) {
        FakeStoreDto productDto = restTemplate.postForObject("https://fakestoreapi.com/products",product,FakeStoreDto.class);

        assert productDto != null;
        return convertFakeStoreProductToProduct(productDto);
    }

    @Override
    public Product updateProduct(Long id,Product product) {

        FakeStoreDto fakeStoreProductDto = new FakeStoreDto();
        fakeStoreProductDto.setTitle(product.getTitle());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setImageUrl(product.getImageUrl());

        FakeStoreDto productDto = restTemplate.patchForObject("https://fakestoreapi.com/products/"+id,product,FakeStoreDto.class);

        assert productDto != null;
        return convertFakeStoreProductToProduct(productDto);
    }

    @Override
    public ResponseEntity<Void> deleteProduct(Long id) {
       return new ResponseEntity<>(HttpStatus.OK);
    }



}
