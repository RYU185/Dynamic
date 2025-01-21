package com.dw.dynamic.DTO;

import com.dw.dynamic.model.Product;
import com.dw.dynamic.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PurchaseHistoryDTO {

    private Long id;

    private Product product;

    private String username;

    private LocalDate purchaseDate; // 구매일

}
