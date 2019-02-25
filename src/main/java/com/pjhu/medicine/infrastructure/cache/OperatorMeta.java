package com.pjhu.medicine.infrastructure.cache;

import com.pjhu.medicine.domain.operator.Operator;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OperatorMeta {

    private String username;
    private String role;

    public static OperatorMeta of(Operator operator) {
        return new OperatorMeta(operator.getUsername(), operator.getRoleAsText());
    }
}
