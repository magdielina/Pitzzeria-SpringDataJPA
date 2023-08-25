package com.platzi.pizza.persistence.audit;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@MappedSuperclass
public class AuditableEntity {

    @Column(columnDefinition = "DATETIME")
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(columnDefinition = "DATETIME")
    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
