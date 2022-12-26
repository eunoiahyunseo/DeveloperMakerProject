package com.hyunseo.programming.dmaker.service;

import com.hyunseo.programming.dmaker.dto.CreateDeveloper;
import com.hyunseo.programming.dmaker.dto.DeveloperDetailDto;
import com.hyunseo.programming.dmaker.dto.DeveloperDto;
import com.hyunseo.programming.dmaker.entity.Developer;
import com.hyunseo.programming.dmaker.exception.DMakerException;
import com.hyunseo.programming.dmaker.repository.DeveloperRepository;
import com.hyunseo.programming.dmaker.type.DeveloperLevel;
import com.hyunseo.programming.dmaker.type.DeveloperSkillType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.hyunseo.programming.dmaker.exception.DMakerErrorCode.*;

@Service
@RequiredArgsConstructor
public class DMakerService {
    // if final constructor argument must have this -> RequiredArgsConstructor
    private final DeveloperRepository developerRepository;
    private final EntityManager em;

    @Transactional
    public CreateDeveloper.Response createDeveloper(CreateDeveloper.Request request) {
        validateCreateDeveloperRequest(request);

        Developer developer = Developer.builder().developerLevel(request.getDeveloperLevel()).developerSkillType(request.getDeveloperSkillType()).experienceYears(request.getExperienceYears()).name(request.getName()).memberId(request.getMemberId()).age(request.getAge()).build();

        developerRepository.save(developer);
        return CreateDeveloper.Response.fromEntity(developer);
    }

    private void validateCreateDeveloperRequest(CreateDeveloper.Request request) {
        // Business validation
        DeveloperLevel developerLevel = request.getDeveloperLevel();
        Integer experienceYears = request.getExperienceYears();

        if (developerLevel == DeveloperLevel.SENIOR && experienceYears < 10) {
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }

        if (developerLevel == DeveloperLevel.JUNGNIOR && (experienceYears < 4 || experienceYears > 10)) {
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }

        if (developerLevel == DeveloperLevel.JUNIOR && experienceYears > 4) {
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }

        developerRepository.findByMemberId(request.getMemberId()).ifPresent((developer -> {
            throw new DMakerException(DUPLICATED_MEMBER_ID);
        }));
    }

    public List<DeveloperDto> getAllDevelopers() {
        return developerRepository.findAll()
                .stream().map(DeveloperDto::fromEntity)
                .collect(Collectors.toList());
    }

    public DeveloperDetailDto getDeveloperDetail(String memberId) {
        return developerRepository.findByMemberId(memberId)
                .map(DeveloperDetailDto::fromEntity)
                .orElseThrow(() -> new DMakerException(NO_DEVELOPER));
    }
}
