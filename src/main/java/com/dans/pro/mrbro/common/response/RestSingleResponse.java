package com.dans.pro.mrbro.common.response;

import com.dans.pro.mrbro.common.constant.ResultCode;
import com.dans.pro.mrbro.common.constant.ResultType;
import com.google.gson.annotations.Expose;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

@Getter
public class RestSingleResponse<T extends Serializable> extends RestResponseBase implements Serializable {

    @Expose
    private T data;

    private RestSingleResponse(ResultCode resultCode, ResultType resultType) {
        this.resultCode = resultCode;
        this.resultType = resultType;
        this.message = resultType.getDesc()+" "+resultCode.getDesc();
    }

    public static <T extends Serializable> RestSingleResponse<T> create(ResultCode resultCode, ResultType resultType, T clazz) {
        return new RestSingleResponse<T>(resultCode, resultType).add(clazz);
    }

    public static <T extends Serializable> RestSingleResponse<T> create(ResultCode resultCode, ResultType resultType, T clazz, String message) {
        return new RestSingleResponse<T>(resultCode, resultType).add(clazz).message(message);
    }

    private RestSingleResponse<T> add(T data) {
        if(data == null) {
            return this;
        }
        this.data = data;
        return this;
    }

    @Override
    public RestSingleResponse<T> message(String message) {
        this.message = message+this.message;
        return this;
    }

    @Override
    public RestSingleResponse<T> responseAt(Date dateTime) {
        this.responseAt = dateTime;
        return this;
    }
}
