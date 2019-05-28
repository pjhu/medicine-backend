package com.pjhu.medicine.common.cache;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserMeta {

    private String username;
    private String role;
}
