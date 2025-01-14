package com.dw.dynamic.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "freelancer")
public class FreeLancer {
    @Id
    @Column(name = "name", updatable = false)
    private String name;

    @Column(name = "amount", updatable = false)
    private Double amount;
}
