package com.dw.dynamic.model;

import com.dw.dynamic.DTO.ProductDTO;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(
        use= JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Course.class,name = "course"),
        @JsonSubTypes.Type(value = PayrollSubscription.class,name = "payrollsubscription")}
)
@Table(name = "product")
public class Product {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "price",nullable = false)
    private Double price;

    @ManyToOne
    @JoinColumn(name = "category_name")
    private Category category; // 제품 - 카테고리 (단방향)

    public ProductDTO toDTO(){
        return new ProductDTO(
                this.id,
                this.price,
                this.category.getName()
        );
    }


}
