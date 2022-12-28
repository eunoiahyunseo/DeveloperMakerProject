package com.hyunseo.programming.dmaker.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.hyunseo.programming.dmaker.constant.DMakerConstant.MAX_JUNIOR_EXPERIENCE_YEARS;
import static com.hyunseo.programming.dmaker.constant.DMakerConstant.MIN_SENIOR_EXPERIENCE_YEARS;

@AllArgsConstructor
@Getter
public enum DeveloperLevel {
    NEW("신입 개발자", 0, 0),
    JUNIOR("주니어 개발자", 1, MAX_JUNIOR_EXPERIENCE_YEARS),
    JUNGNIOR("중니어 기발자",
            MAX_JUNIOR_EXPERIENCE_YEARS,
            MIN_SENIOR_EXPERIENCE_YEARS),
    SENIOR("시니어 개발자", MIN_SENIOR_EXPERIENCE_YEARS, 76);

    private final String description;
    private final Integer minExperienceYears;
    private final Integer maxExperienceYears;
}
