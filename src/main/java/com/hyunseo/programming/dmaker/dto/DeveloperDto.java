package com.hyunseo.programming.dmaker.dto;

import com.hyunseo.programming.dmaker.entity.Developer;
import com.hyunseo.programming.dmaker.type.DeveloperLevel;
import com.hyunseo.programming.dmaker.type.DeveloperSkillType;
import lombok.*;

/**
 * @author ihyeonseo
 * @created 26/12/2022 - 10:00 PM
 * @project dmaker
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeveloperDto {
    private DeveloperLevel developerLevel;
    private DeveloperSkillType developerSkillType;
    private String memberId;

    public static DeveloperDto fromEntity(Developer developer) {
    return DeveloperDto.builder()
            .developerLevel(developer.getDeveloperLevel())
            .developerSkillType(developer.getDeveloperSkillType())
            .memberId(developer.getMemberId())
            .build();
    }
}
