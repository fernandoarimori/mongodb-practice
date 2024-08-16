package com.mongo.mongo.exercise.model.dto;

public record BuyPostDTO(
        String name,
        String email,
        ItemRequestDTO item
) {
}
