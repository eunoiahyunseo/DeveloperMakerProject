package com.hyunseo.programming.dmaker.dto;

import com.hyunseo.programming.dmaker.entity.Developer;
import com.hyunseo.programming.dmaker.exception.DMakerErrorCode;
import com.hyunseo.programming.dmaker.type.DeveloperLevel;
import com.hyunseo.programming.dmaker.type.DeveloperSkillType;
import lombok.*;

import javax.validation.constraints.*;

/**
 * @author ihyeonseo
 * @created 26/12/2022 - 7:23 오후
 * @project dmaker
 */
public class CreateDeveloper {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class Request {
        @NotNull
        private DeveloperLevel developerLevel;
        @NotNull
        private DeveloperSkillType developerSkillType;
        @NotNull
        @Min(0)
        @Max(20)
        private Integer experienceYears;

        @NotNull
        @Size(min = 3, max = 50, message = "memberId size must 3~50")
        private String memberId;
        @NotNull
        @Size(min = 3, max = 20, message = "name size must 3~20")
        private String name;

        @Min(18)
        private Integer age;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class Response {
        private DeveloperLevel developerLevel;
        private DeveloperSkillType developerSkillType;
        private Integer experienceYears;
        private String memberId;

        private DMakerErrorCode errorCode;
        private String errorMessage;

        public static Response fromEntity(@NonNull Developer developer) {
            return Response.builder()
                    .developerLevel(developer.getDeveloperLevel())
                    .developerSkillType(developer.getDeveloperSkillType())
                    .experienceYears(developer.getExperienceYears())
                    .memberId(developer.getMemberId())
                    .build();
        }
    }
}
