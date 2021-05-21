package com.eop.order.core.repositories;

import com.eop.order.core.entities.BaseEntity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class AuditEntityListener {

    @PrePersist()
    public void prePersist(Object entity) {
        if (entity instanceof BaseEntity) {
            ((BaseEntity) entity).setCreatedDate(LocalDateTime.now());
        }
    }

    @PreUpdate
    public void preUpdate(Object entity) {
        if (entity instanceof BaseEntity) {
            ((BaseEntity) entity).setModifiedDate(LocalDateTime.now());
        }
    }
}
