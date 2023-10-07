package com.dans.pro.mrbro.common.response;

import com.dans.pro.mrbro.common.constant.ResultCode;
import com.dans.pro.mrbro.common.constant.ResultType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.annotations.Expose;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

@Getter
public abstract class RestResponseBase<T> implements Serializable {
    @Expose
    protected ResultCode resultCode = ResultCode.SUCCESS;

    @Expose
    protected ResultType resultType;

    @Expose
    protected String message;

    @Expose
    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "Asia/Jakarta", pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date responseAt = new Date();

    public abstract T message(String message);
    public abstract T responseAt(Date dateTime);

}
