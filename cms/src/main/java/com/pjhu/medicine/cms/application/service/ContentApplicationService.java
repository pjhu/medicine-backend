package com.pjhu.medicine.cms.application.service;

import com.pjhu.medicine.cms.application.service.command.ContentCreateCommand;
import com.pjhu.medicine.cms.application.service.command.ContentUpdateCommand;
import com.pjhu.medicine.cms.domain.model.Content;
import com.pjhu.medicine.cms.domain.model.ContentAttribute;
import com.pjhu.medicine.cms.domain.model.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContentApplicationService {

    private final ContentRepository contentRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public Long create(ContentCreateCommand contentCreateCommand) {
        Content content = new Content();
        List<ContentAttribute> contentAttributes = contentCreateCommand.getContentAttributes().stream()
                .map(e -> new ContentAttribute(e.getName(), e.getValue()))
                .collect(Collectors.toList());
        Content newContent = content.create(contentAttributes);
        contentRepository.save(newContent);
        return newContent.getId();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void update(ContentUpdateCommand contentUpdateCommand) {
        Content content = contentRepository.findById(contentUpdateCommand.getContentId())
                .orElseThrow(RuntimeException::new);
        List<ContentAttribute> contentAttributes = contentUpdateCommand.getContentAttributes().stream()
                .map(e -> new ContentAttribute(e.getName(), e.getValue()))
                .collect(Collectors.toList());
        content.update(contentAttributes);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void publish(Long contentId) {
        Content content = contentRepository.findById(contentId)
                .orElseThrow(RuntimeException::new);
        content.publish();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void archive(Long contentId) {
        Content content = contentRepository.findById(contentId)
                .orElseThrow(RuntimeException::new);
        content.archive();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void delete(Long contentId) {
        Content content = contentRepository.findById(contentId)
                .orElseThrow(RuntimeException::new);

        if (content.isDraft()) {
            contentRepository.delete(content);
        } else {
            content.discard();
        }
    }
}
