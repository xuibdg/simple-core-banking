package com.b2camp.simple_core_banking.utils.exception;

import com.b2camp.simple_core_banking.dto.MetaData;
import com.b2camp.simple_core_banking.utils.StringUtils;

public class BusinessException extends BaseException {

    public BusinessException(GlobalErrorMapping errorMapping, String referenceNumber) {
        super(MetaData.builder()
                .status(errorMapping.code)
                .message(errorMapping.message)
                .code(0)
                .build(), (StringUtils.hasValue(referenceNumber)? referenceNumber: null), "");
    }
}
