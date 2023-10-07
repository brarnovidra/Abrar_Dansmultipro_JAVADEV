package com.dans.pro.mrbro.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResultType {
    CREATE("C", "CREATE"),
    READ("R", "READ"),
    UPDATE("U", "UPDATE"),
    DELETE("D", "DELETE"),
    ERROR("E", "ERROR");


    private final String code;
    private final String desc;
}
