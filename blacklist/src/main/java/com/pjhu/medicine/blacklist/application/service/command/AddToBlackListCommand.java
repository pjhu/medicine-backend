package com.pjhu.medicine.blacklist.application.service.command;

import lombok.*;

import javax.validation.constraints.NotNull;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddToBlackListCommand {

    @NotNull
    private Long phone;
}
