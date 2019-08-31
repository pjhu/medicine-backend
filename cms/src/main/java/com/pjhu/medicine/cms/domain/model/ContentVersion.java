package com.pjhu.medicine.cms.domain.model;

import com.pjhu.medicine.common.domain.model.AbstractEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CONTENT_VERSION")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContentVersion extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "content_id")
    private Content content;

    private Integer publishedVersion;
    @Enumerated(value = EnumType.STRING)
    private ContentStatus contentStatus;
    private LocalDateTime publishedAt;
    private LocalDateTime publishedBy;

    @ElementCollection
    @CollectionTable(
            name = "content_attribute",
            joinColumns = @JoinColumn(name = "content_version_id")
    )
    private List<ContentAttribute> contentAttributes = new ArrayList<>();

    public ContentVersion(Content content, List<ContentAttribute> contentAttributes) {
        this.content = content;
        this.contentStatus = ContentStatus.DRAFT;
        this.contentAttributes.addAll(contentAttributes);
    }
}
