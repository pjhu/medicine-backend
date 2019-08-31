package com.pjhu.medicine.cms.domain.model;

import com.google.common.collect.Lists;
import com.pjhu.medicine.common.domain.model.AbstractEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "CONTENT")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Content extends AbstractEntity {

    @Enumerated(value = EnumType.STRING)
    private ContentType contentType;
    @Enumerated(value = EnumType.STRING)
    private ContentStatus contentStatus;
    private Integer publishedVersion;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "content")
    List<ContentVersion> contentVersions;

    public Content(ContentType contentType, List<ContentAttribute> contentAttributes) {
        this.contentType = contentType;
        this.contentStatus = ContentStatus.DRAFT;
        ContentVersion contentVersion = new ContentVersion(this, contentAttributes);
        this.contentVersions = new ArrayList<>(Lists.newArrayList(contentVersion));
    }

    public Content create(List<ContentAttribute> contentAttributes) {
        return new Content(ContentType.BRAND, contentAttributes);
    }

    public void update(List<ContentAttribute> contentAttributes) {
        this.contentVersions.stream()
                .filter(e -> ContentStatus.DRAFT.equals(e.getContentStatus()))
                .forEach(e -> {
                    e.setContentStatus(ContentStatus.EDITING);
                    e.setContentAttributes(contentAttributes);
                });
    }

    public void publish() {
        this.contentStatus = ContentStatus.PUBLISHED;
        this.contentVersions.stream()
                .filter(e -> ContentStatus.PUBLISHED.equals(e.getContentStatus()))
                .forEach(e -> e.setContentStatus(ContentStatus.ARCHIVED));
        List<ContentVersion> contentVersions = this.contentVersions.stream()
                .filter(e -> ContentStatus.DRAFT.equals(e.getContentStatus())
                        || ContentStatus.EDITING.equals(e.getContentStatus()))
                .map(e -> ContentVersion.builder()
                        .content(this)
                        .contentAttributes(Lists.newArrayList(e.getContentAttributes()))
                        .contentStatus(ContentStatus.PUBLISHED)
                        .publishedAt(LocalDateTime.now())
                        .build())
                .collect(Collectors.toList());
        this.contentVersions.addAll(contentVersions);
    }

    public boolean isDraft() {
        return ContentStatus.DRAFT.equals(this.contentStatus);
    }

    public void discard() {
        ContentVersion contentVersion = this.contentVersions.stream()
                .filter(e -> ContentStatus.PUBLISHED.equals(e.getContentStatus()))
                .findFirst().orElseThrow(RuntimeException::new);
        this.contentVersions.stream()
                .filter(e -> ContentStatus.EDITING.equals(e.getContentStatus()))
                .forEach(e -> {
                    e.setContentStatus(ContentStatus.DRAFT);
                    e.setContentAttributes(contentVersion.getContentAttributes());
                });

    }

    public void archive() {
        if (ContentStatus.PUBLISHED.equals(this.contentStatus)) {
            this.contentStatus = ContentStatus.ARCHIVED;
        }
    }
}
