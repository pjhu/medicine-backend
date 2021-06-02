package com.pjhu.medicine.blacklist.application.service.command;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CheckInBlacklistCommand {

    private Long phone;
}
