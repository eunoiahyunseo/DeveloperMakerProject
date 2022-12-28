package com.hyunseo.programming.dmaker.entity;

import com.hyunseo.programming.dmaker.code.StatusCode;
import com.hyunseo.programming.dmaker.dto.CreateDeveloper;
import com.hyunseo.programming.dmaker.type.DeveloperSkillType;
import com.hyunseo.programming.dmaker.type.DeveloperLevel;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Developer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Enumerated(EnumType.STRING)
    private DeveloperSkillType developerSkillType;

    @Enumerated(EnumType.STRING)
    private DeveloperLevel developerLevel;

    private Integer experienceYears;
    private String memberId;
    private String name;
    private Integer age;

    @Enumerated(EnumType.STRING)
    private StatusCode statusCode;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;


}
