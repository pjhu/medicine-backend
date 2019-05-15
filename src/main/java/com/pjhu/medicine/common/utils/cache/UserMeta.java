package com.pjhu.medicine.common.utils.cache;

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
