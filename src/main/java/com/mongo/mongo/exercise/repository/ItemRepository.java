package com.mongo.mongo.exercise.repository;

import com.mongo.mongo.exercise.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemRepository extends MongoRepository<Item, Long> {
    Item findByBuyId(Long id);
}
