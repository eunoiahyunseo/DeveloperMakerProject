package com.hyunseo.programming.dmaker.service;

import com.hyunseo.programming.dmaker.code.StatusCode;
import com.hyunseo.programming.dmaker.dto.*;
import com.hyunseo.programming.dmaker.entity.Developer;
import com.hyunseo.programming.dmaker.entity.RetiredDeveloper;
import com.hyunseo.programming.dmaker.exception.DMakerException;
import com.hyunseo.programming.dmaker.repository.DeveloperRepository;
import com.hyunseo.programming.dmaker.repository.RetiredDeveloperRepository;
import com.hyunseo.programming.dmaker.type.DeveloperLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.hyunseo.programming.dmaker.constant.DMakerConstant.MAX_JUNIOR_EXPERIENCE_YEARS;
import static com.hyunseo.programming.dmaker.constant.DMakerConstant.MIN_SENIOR_EXPERIENCE_YEARS;
import static com.hyunseo.programming.dmaker.exception.DMakerErrorCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class DMakerService {
    // if final constructor argument must have this -> RequiredArgsConstructor
    private final DeveloperRepository developerRepository;
    private final RetiredDeveloperRepository retiredDeveloperRepository;
    // private final EntityManager em;

    private Developer CreateDeveloperFromRequest(CreateDeveloper.Request request) {
        return Developer.builder()
                .developerLevel(request.getDeveloperLevel())
                .developerSkillType(request.getDeveloperSkillType())
                .experienceYears(request.getExperienceYears())
                .name(request.getName())
                .memberId(request.getMemberId())
                .statusCode(StatusCode.EMPLOYED)
                .age(request.getAge())
                .build();
    }

    @Transactional(readOnly = true)
    public CreateDeveloper.Response createDeveloper(
            CreateDeveloper.Request request
    ) {
        validateCreateDeveloperRequest(request);

        // business logic start
        // One-time variables are not made into regional variables.
        return CreateDeveloper.Response.fromEntity(
                developerRepository.save(
                        CreateDeveloperFromRequest(request)
                )
        );
    }

    private static void validateDeveloperLevel(
            DeveloperLevel developerLevel,
            Integer experienceYears
    ) {
        if (experienceYears < developerLevel.getMinExperienceYears() ||
            experienceYears > developerLevel.getMaxExperienceYears()
        ) {
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }
    }

    private void validateCreateDeveloperRequest(
            @NonNull CreateDeveloper.Request request
    ) {

        validateDeveloperLevel(
                request.getDeveloperLevel(),
                request.getExperienceYears()
        );

        developerRepository.findByMemberId(request.getMemberId())
                .ifPresent((developer -> {
                    throw new DMakerException(DUPLICATED_MEMBER_ID);
                }));
    }


    @Transactional(readOnly = true)
    public List<DeveloperDto> getAllEmployedDevelopers() {
        return developerRepository
                .findDevelopersByStatusCodeEquals(StatusCode.EMPLOYED)
                .stream()
                .map(DeveloperDto::fromEntity)
                .collect(Collectors.toList());
    }

    // get has value -> so when hasn't value we occur exception
    private Developer getDeveloperMemberById(
            String memberId
    ) {
        return developerRepository.findByMemberId(memberId)
                .orElseThrow(() -> new DMakerException(NO_DEVELOPER));
    }

    @Transactional(readOnly = true)
    public DeveloperDetailDto getDeveloperDetail(
            String memberId
    ) {
        return DeveloperDetailDto.fromEntity(
                getDeveloperMemberById(memberId)
        );
    }

    private Developer getUpdatedDeveloperFromRequest(
            EditDeveloper.Request request,
            Developer developer
    ) {
        developer.setDeveloperLevel(request.getDeveloperLevel());
        developer.setDeveloperSkillType(request.getDeveloperSkillType());
        developer.setExperienceYears(request.getExperienceYears());

        return developer;
    }

    @Transactional
    public DeveloperDetailDto editDeveloper(
            String memberId,
            EditDeveloper.Request request) {

        // validation
        validateDeveloperLevel(
                request.getDeveloperLevel(),
                request.getExperienceYears()
        );

        return DeveloperDetailDto.fromEntity(
                getUpdatedDeveloperFromRequest(
                        request,
                        getDeveloperMemberById(memberId)
                )
        );
    }

    @Transactional()
    public DeveloperDetailDto deleteDeveloper(
            String memberId
    ) {
        // 1. EMPLOYED -> RETIRED
        Developer developer = developerRepository.findByMemberId(memberId)
                .orElseThrow(() -> new DMakerException(NO_DEVELOPER));
        developer.setStatusCode(StatusCode.RETIRED);

        // 2. save into RetiredDeveloper
        RetiredDeveloper retiredDeveloper = RetiredDeveloper.builder()
                .memberId(memberId)
                .name(developer.getName())
                .build();

        retiredDeveloperRepository.save(retiredDeveloper);

        return DeveloperDetailDto.fromEntity(developer);
    }
}
