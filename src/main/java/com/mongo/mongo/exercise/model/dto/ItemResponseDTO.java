package com.mongo.mongo.exercise.model.dto;

import com.mongo.mongo.exercise.model.Item;

public record ItemResponseDTO(
        Long id,
        String description,
        String name,
        Double price
) {
    public ItemResponseDTO(Item item) {
        this(item.getId(), item.getDescription(), item.getName(), item.getPriceItem());
    }
}
