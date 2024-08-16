package com.mongo.mongo.exercise.model;

import com.mongo.mongo.exercise.model.dto.BuyPostDTO;
import com.mongo.mongo.exercise.model.dto.ItemRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Document(collection = "tb_item")
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    @MongoId(FieldType.DOUBLE)
    private Long id;
    private Long buyId;
    private String description;
    private String name;
    private Double priceItem;

    public Item(ItemRequestDTO item, Long buyId) {
        this.buyId = buyId;
        this.description = item.description();
        this.name = item.name();
        this.priceItem = item.price();
    }

    public Item(ItemRequestDTO item) {
        this.description = item.description();
        this.name = item.name();
        this.priceItem = item.price();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBuyId(Long buyId) {
        this.buyId = buyId;
    }

    public void update(ItemRequestDTO item) {
        this.description = item.description();
        this.name = item.name();
        this.priceItem = item.price();
    }
}
