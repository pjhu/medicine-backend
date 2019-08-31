package com.pjhu.medicine.cms.application.service.command;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContentUpdateCommand {

    private Long contentId;
    private List<ContentAttributeCommand> contentAttributes = new ArrayList<>();
}
