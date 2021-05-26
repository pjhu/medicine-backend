package com.pjhu.medicine.common.cache;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExternalUserMeta {

    private String username;
    private String role;
}
