package com.pjhu.medicine.cms.application.service;

import com.pjhu.medicine.cms.application.service.command.ContentAttributeCommand;
import com.pjhu.medicine.cms.application.service.response.ContentResponse;
import com.pjhu.medicine.cms.domain.model.ContentQueryRepository;
import com.pjhu.medicine.cms.domain.model.ContentType;
import com.pjhu.medicine.cms.domain.model.ContentWithAttribute;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContentQueryService {

    private final ContentQueryRepository contentQueryRepository;

    @Transactional(readOnly = true)
    public Page<ContentResponse> findContentsForAdmin(ContentType contentType, Pageable pageable) {
        Page<ContentWithAttribute> contentWithAttributes = contentQueryRepository
                .findAllForAdmin(contentType, pageable);
        Map<Long, List<ContentWithAttribute>> contentMap = contentWithAttributes.stream()
                .collect(Collectors.groupingBy(ContentWithAttribute::getContentVersionId, Collectors.toList()));
        List<ContentResponse> contentResponses = contentMap.keySet().stream()
                .map(contentVersionId -> {
                    ContentWithAttribute contentWithVersion = contentMap.get(contentVersionId).stream()
                            .findAny().orElseThrow(RuntimeException::new);
                    List<ContentAttributeCommand> attrs = contentMap.get(contentVersionId).stream()
                            .map(attr -> ContentAttributeCommand.builder()
                                    .name(attr.getName())
                                    .value(attr.getValue())
                                    .build())
                            .collect(Collectors.toList());
                    return ContentResponse.builder()
                            .id(contentWithVersion.getContentId())
                            .contentStatus(contentWithVersion.getContentStatus())
                            .contentAttributes(attrs)
                            .build();
                })
                .collect(Collectors.toList());
        return new PageImpl<>(contentResponses, contentWithAttributes.getPageable(),
                contentResponses.size());
    }
}
