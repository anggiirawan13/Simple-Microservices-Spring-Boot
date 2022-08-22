package com.anggi.irawan.springcloudcifservice.models.base;

import lombok.*;
import lombok.experimental.SuperBuilder;
import net.bytebuddy.implementation.bind.annotation.Super;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BaseModel {

    @Column(nullable = false, length = 30)
    private String createdBy;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "DATE")
    private LocalDate createdAt;

    @Column(nullable = false, length = 30)
    private String updatedBy;

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "DATE")
    private LocalDate updatedAt;

    @Column(nullable = false, length = 30)
    private boolean isDeleted = false;
}