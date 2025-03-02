package com.dw.dynamic.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PointDTO {

    private Long id;

    private Long amount;

    private Long purchaseHistoryId;

    private LocalDate addDate;
}
