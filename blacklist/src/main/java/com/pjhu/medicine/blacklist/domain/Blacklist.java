package com.pjhu.medicine.blacklist.domain;

import com.pjhu.medicine.common.domain.model.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "blacklist")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Blacklist extends AbstractEntity {

    private Long phone;

}
