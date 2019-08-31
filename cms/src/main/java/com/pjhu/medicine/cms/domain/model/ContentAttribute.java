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

    private String name;
    private String value;
}
