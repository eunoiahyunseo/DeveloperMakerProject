package com.hyunseo.programming.dmaker.controller;

import com.hyunseo.programming.dmaker.dto.CreateDeveloper;
import com.hyunseo.programming.dmaker.dto.DeveloperDetailDto;
import com.hyunseo.programming.dmaker.dto.DeveloperDto;
import com.hyunseo.programming.dmaker.service.DMakerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DMakerController {
    // 자동으로 injection 해준다.
    private final DMakerService dMakerService;

    @GetMapping("/developers")
    public List<DeveloperDto> getAllDevelopers() {
        log.info("GET /developers HTTP/1.1");
        return dMakerService.getAllDevelopers();
    }

    @GetMapping("/developer/{memberId}")
    public DeveloperDetailDto getDeveloperDetail(
            @PathVariable String memberId
    ) {
        log.info("GET /developers HTTP/1.1");
        return dMakerService.getDeveloperDetail(memberId);
    }


    @PostMapping("/create-developers")
    public CreateDeveloper.Response createDevelopers(
            @Valid @RequestBody CreateDeveloper.Request request
    ) {
        log.info("request : {}", request);

        return dMakerService.createDeveloper(request);
    }
}
