package com.pjhu.medicine.cms.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContentWithAttribute {
    private Long contentId;
    private Long contentVersionId;
    private LocalDateTime publishedAt;
    private ContentStatus contentStatus;
    private String name;
    private String value;
}
