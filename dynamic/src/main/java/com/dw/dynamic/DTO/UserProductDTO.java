package com.dw.dynamic.DTO;


import com.dw.dynamic.model.Product;
import com.dw.dynamic.model.User;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserProductDTO {

    private Long id;

    private String username;

    private Product product;

}
