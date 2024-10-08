package com.mongo.mongo.exercise.model.dto;

import com.mongo.mongo.exercise.model.Item;

import java.util.List;

public record BuyPostDTO(
        String name,
        String email,
        List<ItemRequestDTO>  item
) {
}
