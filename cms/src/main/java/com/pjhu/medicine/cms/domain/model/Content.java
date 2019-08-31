package com.pjhu.medicine.cms.domain.model;

import com.pjhu.medicine.common.domain.model.AbstractEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private Integer publishedVersion;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "content")
    List<ContentVersion> contentVersions = new ArrayList<>();
}
