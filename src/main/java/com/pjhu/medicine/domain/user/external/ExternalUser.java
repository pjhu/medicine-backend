package com.pjhu.medicine.domain.user.external;

import com.pjhu.medicine.domain.user.Role;
import com.pjhu.medicine.domain.user.UserStatus;
import com.pjhu.medicine.infrastructure.persistence.AbstractEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "external_user")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExternalUser extends AbstractEntity {

    private String username;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private UserStatus active;
}
