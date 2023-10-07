package com.dans.pro.mrbro.common.response;

import com.dans.pro.mrbro.common.constant.ResultCode;
import com.dans.pro.mrbro.common.constant.ResultType;
import com.google.gson.annotations.Expose;
import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
public class RestListResponse<T extends Serializable> extends RestResponseBase implements Serializable {

    @Expose
    private Integer listCount = 0;

    @Expose
    private List<T> data = new ArrayList<>();

    private RestListResponse(ResultCode resultCode, ResultType resultType) {
        this.resultCode = resultCode;
        this.resultType = resultType;
        this.message = resultType.getDesc()+" "+resultCode.getDesc();
    }

    public static <T extends Serializable> RestListResponse<T> create(ResultCode resultCode, ResultType resultType, T clazz) {
        return new RestListResponse<T>(resultCode, resultType).add(clazz).listCount(clazz == null ? 0 : 1);
    }

    public static <T extends Serializable> RestListResponse<T> create(ResultCode resultCode, ResultType resultType, T... clazz) {
        Integer listCount = 0;
        if(clazz != null && clazz.length != 0) {
            listCount = clazz.length;
        }
        return new RestListResponse<T>(resultCode, resultType).add(clazz).listCount(listCount);
    }

    public static <T extends Serializable> RestListResponse<T> create(ResultCode resultCode, ResultType resultType, List<T> clazz) {
        Integer listCount = 0;
        if(clazz != null && !clazz.isEmpty()) {
            listCount = clazz.size();
        }
        return new RestListResponse<T>(resultCode, resultType).add(clazz).listCount(listCount);
    }

    public static <T extends Serializable> RestListResponse<T> create(ResultCode resultCode, ResultType resultType, List<T> clazz, String message) {
        Integer listCount = 0;
        if(clazz != null && !clazz.isEmpty()) {
            listCount = clazz.size();
        }
        return new RestListResponse<T>(resultCode, resultType).add(clazz).listCount(listCount).message(message);
    }

    public RestListResponse<T> add(T t) {
        if (t == null) {
            return this;
        }
        data.add(t);
        return this;
    }

    public RestListResponse<T> add(T... ts) {
        if (ts == null || ts.length == 0) {
            return this;
        }
        for (T t : ts) {
            add(t);
        }
        return this;
    }

    public RestListResponse<T> add(List<T> list) {
        if (list == null || list.isEmpty()) {
            return this;
        }
        data.addAll(list);
        return this;
    }

    private RestListResponse<T> listCount(Integer listCount) {
        this.listCount = listCount;
        if(this.listCount == null) {
            this.listCount = 0;
        }
        return this;
    }

    @Override
    public RestListResponse message(String message){
        this.message = message+this.message;
        return this;
    }

    @Override
    public RestListResponse responseAt(Date dateTime){
        this.responseAt = dateTime;
        return this;
    }
}
