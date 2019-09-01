package com.pjhu.medicine.cms.application.service.response;

import com.pjhu.medicine.cms.application.service.command.ContentAttributeCommand;
import com.pjhu.medicine.cms.domain.model.ContentStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ContentResponse {

    private Long id;
    private ContentStatus contentStatus;
    private List<ContentAttributeCommand> contentAttributes;
}
