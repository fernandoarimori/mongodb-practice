package com.mongo.mongo.exercise.model.dto;

public record ItemRequestDTO(
        String description,
        String name,
        Double price
) {
}
