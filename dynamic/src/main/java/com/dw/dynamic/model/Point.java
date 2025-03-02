package com.dw.dynamic.model;

import com.dw.dynamic.DTO.PointDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name = "point")
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "add_date")
    private LocalDate addDate;

    @OneToOne
    @JoinColumn(name = "purchase_history_id")
    private PurchaseHistory purchaseHistory;

    public PointDTO toDTO(){
        return new PointDTO(
                this.id,
                this.amount,
                this.purchaseHistory.getId(),
                this.addDate
        );
    }
}
