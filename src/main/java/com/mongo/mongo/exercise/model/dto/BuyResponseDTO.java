package com.mongo.mongo.exercise.model.dto;

import com.mongo.mongo.exercise.model.Buy;
import com.mongo.mongo.exercise.model.Item;

import java.math.BigInteger;

public record BuyResponseDTO(
        Long id,
        String name,
        String email,
        Item item
) {
    public BuyResponseDTO(Buy savedOne
//            , Item item
    ) {
        this(savedOne.getId(), savedOne.getName(), savedOne.getEmail(), savedOne.getItem());
    }
}
