package com.hyunseo.programming.dmaker.controller;

import com.hyunseo.programming.dmaker.dto.*;
import com.hyunseo.programming.dmaker.exception.DMakerException;
import com.hyunseo.programming.dmaker.service.DMakerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
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
        return dMakerService.getAllEmployedDevelopers();
    }

    @Transactional
    @GetMapping("/developer/{memberId}")
    public DeveloperDetailDto getDeveloperDetail(
            @PathVariable final String memberId) {
        log.info("GET /developers HTTP/1.1");

        return dMakerService.getDeveloperDetail(memberId);
    }

    @PostMapping("/create-developers")
    public CreateDeveloper.Response createDevelopers(
            @Valid @RequestBody final CreateDeveloper.Request request) {
        log.info("request : {}", request);

        return dMakerService.createDeveloper(request);
    }

    @PutMapping("/developer/{memberId}")
    public DeveloperDetailDto editDevelopers(
            @PathVariable final String memberId,
            @Valid @RequestBody final EditDeveloper.Request request) {
        return dMakerService.editDeveloper(memberId, request);
    }

    @DeleteMapping("/developer/{memberId}")
    public DeveloperDetailDto deleteDeveloper(
            @PathVariable final String memberId) {
        return dMakerService.deleteDeveloper(memberId);
    }
}
