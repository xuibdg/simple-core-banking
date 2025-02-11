package com.b2camp.simple_core_banking.utils.exception;

import com.b2camp.simple_core_banking.dto.MetaData;

public class BaseException extends RuntimeException {
    public MetaData metaData;
    protected String referenceNumber;

    public BaseException(MetaData metaData, String referenceNumber, String rootCause) {
        super(rootCause);
        this.metaData = metaData;
        this.referenceNumber = referenceNumber;
    }
}
