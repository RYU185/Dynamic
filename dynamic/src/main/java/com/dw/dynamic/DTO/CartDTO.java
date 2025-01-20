package com.dw.dynamic.DTO;


import com.dw.dynamic.model.Product;
import com.dw.dynamic.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartDTO {

    private Long cartId;

    private String username;

    private String productId;

}
