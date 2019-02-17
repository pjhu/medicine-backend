package com.pjhu.medicine.infrastructure.persistence;

import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;
import java.time.LocalDateTime;

@MappedSuperclass
@ToString(callSuper = true)
public abstract class Entity extends Identifiable {
    private String createdBy;
    private LocalDateTime createdAt;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedAt;

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