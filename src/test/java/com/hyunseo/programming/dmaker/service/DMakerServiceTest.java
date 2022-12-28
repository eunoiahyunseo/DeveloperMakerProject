package com.hyunseo.programming.dmaker.service;

import com.fasterxml.jackson.databind.deser.std.StdKeyDeserializer;
import com.hyunseo.programming.dmaker.code.StatusCode;
import com.hyunseo.programming.dmaker.dto.CreateDeveloper;
import com.hyunseo.programming.dmaker.dto.DeveloperDetailDto;
import com.hyunseo.programming.dmaker.dto.DeveloperDto;
import com.hyunseo.programming.dmaker.entity.Developer;
import com.hyunseo.programming.dmaker.exception.DMakerErrorCode;
import com.hyunseo.programming.dmaker.exception.DMakerException;
import com.hyunseo.programming.dmaker.repository.DeveloperRepository;
import com.hyunseo.programming.dmaker.repository.RetiredDeveloperRepository;
import com.hyunseo.programming.dmaker.type.DeveloperLevel;
import com.hyunseo.programming.dmaker.type.DeveloperSkillType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static com.hyunseo.programming.dmaker.exception.DMakerErrorCode.DUPLICATED_MEMBER_ID;
import static com.hyunseo.programming.dmaker.exception.DMakerErrorCode.INTERNAL_SERVER_ERROR;
import static com.hyunseo.programming.dmaker.type.DeveloperLevel.JUNIOR;
import static com.hyunseo.programming.dmaker.type.DeveloperLevel.SENIOR;
import static com.hyunseo.programming.dmaker.type.DeveloperSkillType.FRONT_END;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author ihyeonseo
 * @created 28/12/2022 - 1:34 PM
 * @project dmaker
 */
@ExtendWith(MockitoExtension.class)
class DMakerServiceTest {

    private final Developer defaultDeveloper = Developer.builder()
            .developerLevel(SENIOR)
            .developerSkillType(FRONT_END)
            .experienceYears(12)
            .statusCode(StatusCode.EMPLOYED)
            .name("name")
            .age(12)
            .build();
    private final CreateDeveloper.Request defaultCreateRequest = CreateDeveloper.Request.builder()
            .developerLevel(SENIOR)
            .developerSkillType(FRONT_END)
            .experienceYears(12)
            .name("name")
            .memberId("memberId")
            .age(32)
            .build();
    @Mock
    private DeveloperRepository developerRepository;
    @Mock
    private RetiredDeveloperRepository retiredDeveloperRepository;
    @InjectMocks
    private DMakerService dMakerService;

    @Test
    public void testSomething() {
        // given
        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.of(defaultDeveloper));

        // when
        DeveloperDetailDto developerDetail = dMakerService.getDeveloperDetail("memberId");

        // then
        assertEquals(SENIOR, developerDetail.getDeveloperLevel());
        assertEquals(FRONT_END, developerDetail.getDeveloperSkillType());
        assertEquals(12, developerDetail.getExperienceYears());
    }

    @Test
    void createDeveloperTest_success() {
        //given
        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.empty());

        ArgumentCaptor<Developer> captor =
                ArgumentCaptor.forClass(Developer.class);

        //when
        CreateDeveloper.Response developer =
                dMakerService.createDeveloper(defaultCreateRequest);

        //then
        verify(developerRepository, times(1))
                .save(captor.capture());

        Developer savedDeveloper = captor.getValue();
        assertEquals(SENIOR, savedDeveloper.getDeveloperLevel());
        assertEquals(FRONT_END, savedDeveloper.getDeveloperSkillType());
        assertEquals(12, savedDeveloper.getExperienceYears());
    }

    @Test
    void createDeveloperTest_failed_with_duplicated() {
        //given
        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.of(defaultDeveloper));

        ArgumentCaptor<Developer> captor =
                ArgumentCaptor.forClass(Developer.class);

        //when
        //then
        DMakerException dMakerException = assertThrows(DMakerException.class,
                () -> dMakerService.createDeveloper(defaultCreateRequest)
        );

//        assertEquals(DUPLICATED_MEMBER_ID, dMakerException.getDMakerErrorCode());
        assertEquals(INTERNAL_SERVER_ERROR, dMakerException.getDMakerErrorCode());



    }
}