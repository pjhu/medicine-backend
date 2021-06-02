package com.pjhu.medicine.blacklist.application.service.response;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CheckInBlacklistResponse {

    private Boolean existed;
}
