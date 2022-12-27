package com.hyunseo.programming.dmaker.dto;

import com.hyunseo.programming.dmaker.exception.DMakerErrorCode;
import lombok.*;

/**
 * @author ihyeonseo
 * @created 27/12/2022 - 3:09 PM
 * @project dmaker
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DMakerErrorResponse {
    private DMakerErrorCode errorCode;
    private String errorMessage;
}
