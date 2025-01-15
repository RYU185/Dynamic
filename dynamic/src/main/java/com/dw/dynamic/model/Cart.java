package com.dw.dynamic.model;

import com.dw.dynamic.DTO.CartDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @ManyToOne
    @JoinColumn(name = "user_name")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name="is_active")
    private Boolean isActive = true;

    public CartDTO toDTO(){
        return new CartDTO(
                this.cartId,
                this.user.getUserName(),
                this.product.toDTO()
        );
    }

}
