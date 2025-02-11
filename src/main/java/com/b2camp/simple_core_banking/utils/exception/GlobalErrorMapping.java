package com.b2camp.simple_core_banking.utils.exception;

import java.util.Arrays;

public enum GlobalErrorMapping {
    SYSTEM_ERROR("-1", "Error silahkan kontak tim kami"),
    INTERNAL_ERROR("666", "Internal Error"),
    SUCCESS("0", "ok"),
    ERROR("1", "ERROR"),
    BAD_REQUEST("444", "Bad Request"),
    INSUFFICIENT_BALANCE("IEG-1000", "Insufficient Balance"),
    DATA_NOT_FOUND("IEG-1001", "Data not Found"),
    INVALID_MANDANTORY_PARAMETER("IEG-1002", "Invalid mandatory parameter"),
    INVALID_CHANNEL("IEG-1003", "Invalid Channel"),
    INVALID_TRANSACTION_TYPE("IEG-1004", "Invalid Transaction Type"),
    INVALID_MSISDN("IEG-1005", "Invalid MSISDN"),
    ALREADY_REFUND("IEG-1006", "Transaction Already Refund"),
    USER_NOT_FOUND("IEG-1007", "User Not Found");

    public final String code;
    public final String message;

    GlobalErrorMapping(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static GlobalErrorMapping getByCode(String code) {
        return Arrays.stream(values())
                .filter(data -> data.code.equals(code))
                .findFirst().orElse(SYSTEM_ERROR);
    }
}
