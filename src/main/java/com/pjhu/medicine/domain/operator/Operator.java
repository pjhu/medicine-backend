package com.pjhu.medicine.domain.operator;

import com.pjhu.medicine.infrastructure.persistence.Entity;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "operator")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Operator extends Entity {

    private String username;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private UserStatus active;

    public String getRoleAsText() {
        return role.name();
    }
}
