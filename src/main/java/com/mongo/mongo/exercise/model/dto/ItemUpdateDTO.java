package com.mongo.mongo.exercise.model.dto;

public record ItemUpdateDTO(
        String description,
        String name,
        Double price
) {
}
