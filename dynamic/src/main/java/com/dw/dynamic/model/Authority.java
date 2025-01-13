package com.dw.dynamic.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "authority")
public class Authority {
    @Id
    @Column(name = "authority_name", length = 255)
    private String authorityName;

}
