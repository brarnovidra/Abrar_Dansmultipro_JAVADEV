package com.dans.pro.mrbro.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResultCode {

    SUCCESS("S", "SUCCESS"), FAIL("F", "FAIL");

    private final String code;
    private final String desc;
}
