package com.mongo.mongo.exercise.model.dto;

import com.mongo.mongo.exercise.model.Buy;
import com.mongo.mongo.exercise.model.Item;

import java.time.LocalDateTime;
import java.util.List;

public record BuyResponseDTO(
        Long id,
        String name,
        String email,
        List<Item> item,
        LocalDateTime postDate,
        LocalDateTime updateDate
) {
    public BuyResponseDTO(Buy savedOne) {
        this(savedOne.getId(), savedOne.getName(), savedOne.getEmail(), savedOne.getItem(), savedOne.getPostDate(), savedOne.getUpdateDate());
    }
}
