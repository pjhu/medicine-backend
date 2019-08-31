package com.pjhu.medicine.cms.domain.model;

import lombok.*;

import javax.persistence.Embeddable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ContentAttribute {

    private Long contentId;
    private Integer publishedVersion;
    private String key;
    private String value;
}
