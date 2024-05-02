package com.example.black_horse.infrastructure.web.apimodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentResponse {
    private String tradeNo;
    private String tradeState;
    private String codeUrl;
}
