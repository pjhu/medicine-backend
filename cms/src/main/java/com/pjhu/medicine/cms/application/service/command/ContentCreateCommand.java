package com.pjhu.medicine.cms.application.service.command;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContentCreateCommand {

    List<ContentAttributeCommand> contentAttributes = new ArrayList<>();
}
