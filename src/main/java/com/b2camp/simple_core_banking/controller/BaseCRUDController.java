package com.b2camp.simple_core_banking.controller;

import com.b2camp.simple_core_banking.dto.BaseApiResponse;
import com.b2camp.simple_core_banking.dto.BaseResponse;
import com.b2camp.simple_core_banking.dto.MetaData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public abstract class BaseCRUDController {

    public static BaseResponse buildSuccessResponse(Object data) {
        return BaseResponse.builder()
                .meta(MetaData.builder()
                        .code(HttpStatus.OK.value())
                        .message(HttpStatus.OK.getReasonPhrase())
                        .status("0").build())
                .data(data)
                .build();
    }
    public static <T extends BaseApiResponse> T buildBaseSuccessApiResponse(T response) {
        response.setMeta(MetaData.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.toString())
                .status("0")
                .build());
        return response;
    }
}
