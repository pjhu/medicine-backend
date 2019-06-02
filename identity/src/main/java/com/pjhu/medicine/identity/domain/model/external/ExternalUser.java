package com.pjhu.medicine.identity.domain.model.external;

import com.pjhu.medicine.common.domain.model.AbstractEntity;
import com.pjhu.medicine.identity.domain.model.Role;
import com.pjhu.medicine.identity.domain.model.UserStatus;
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
