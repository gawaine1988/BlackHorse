package com.example.black_horse.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Order {
    private String tradeNo;
    private String tradeState;
    private String contractId;
    private String codeUrl;
}
