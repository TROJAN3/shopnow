package com.cdb.shopnow.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FakeStoreDto {
    private Long id;
    private String title;
    private double price;
    private String category;
    private String description;
    private String imageUrl;
}
