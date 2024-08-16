package com.mongo.mongo.exercise.model.dto;

public record ItemUpdateDTO(
        Long id,
        Long buyId,
        String description,
        String name,
        Double price
) {
}
