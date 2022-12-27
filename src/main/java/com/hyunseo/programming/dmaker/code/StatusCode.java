package com.hyunseo.programming.dmaker.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ihyeonseo
 * @created 27/12/2022 - 1:59 PM
 * @project dmaker
 */
@Getter
@AllArgsConstructor
public enum StatusCode {
    EMPLOYED("고용"),
    RETIRED("퇴직");

    private final String description;
}
