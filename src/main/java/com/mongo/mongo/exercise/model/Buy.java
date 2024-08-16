package com.mongo.mongo.exercise.model;

import com.mongo.mongo.exercise.model.dto.BuyPostDTO;
import com.mongo.mongo.exercise.model.dto.ItemRequestDTO;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "tb_buy")
public class Buy {
    @MongoId(FieldType.DOUBLE)
    private Long id;
    private String name;
    private String email;
    private Double price;
    @Indexed
    @Field(targetType = FieldType.STRING)
    private PayCond payCond;
    private Item item;
    private LocalDateTime postDate;
    private LocalDateTime updateDate;

    public Buy(BuyPostDTO buyPostDTO, Item item) {
        this.name = buyPostDTO.name();
        this.email = buyPostDTO.email();
        this.price = null;
        this.payCond = PayCond.MADE;
        this.item = item;
        this.postDate = LocalDateTime.now(ZoneId.systemDefault());
    }

    public void update(BuyPostDTO dto, Item  item) {
        this.name = dto.name();
        this.email = dto.email();
        this.item = item;
        this.updateDate = LocalDateTime.now();
    }
}
