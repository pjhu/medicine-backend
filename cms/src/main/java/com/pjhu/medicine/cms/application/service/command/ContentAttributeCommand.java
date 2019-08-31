package com.pjhu.medicine.cms.application.service.command;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContentAttributeCommand {
    private String name;
    private String value;
}
