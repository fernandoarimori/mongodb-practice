package com.mongo.mongo.exercise.repository;

import com.mongo.mongo.exercise.model.Buy;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;

public interface BuyRepository extends MongoRepository<Buy, Long> {
}
