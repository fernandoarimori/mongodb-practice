package com.mongo.mongo.exercise.model.dto;

import java.util.List;

public record BuyEditDTO(
        String name,
        String email,
        List<ItemUpdateDTO>  item
) {
}
