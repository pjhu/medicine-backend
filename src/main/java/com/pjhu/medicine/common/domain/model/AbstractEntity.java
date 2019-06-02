package com.pjhu.medicine.common.domain.model;

import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@ToString(callSuper = true)
public abstract class AbstractEntity {

    @Id
    private Long id;
    @CreatedBy
    private String createdBy;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedBy
    private String lastModifiedBy;
    @LastModifiedDate
    private LocalDateTime lastModifiedAt;

    public AbstractEntity() {
        this.id = IdGenerator.nextIdentity();
    }

    public Long getId() {
        return id;
    }

    public void forceUpdateForAuditing() {
        this.lastModifiedAt = LocalDateTime.now();
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getLastModifiedAt() {
        return lastModifiedAt;
    }

    public LocalDate getLastModifiedDate() {
        return null == lastModifiedAt ? null : lastModifiedAt.toLocalDate();
    }

    protected void updateCreateAtDate() {
        this.createdAt = LocalDateTime.now();
    }
}
