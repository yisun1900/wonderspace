package com.wonderwan.wonderspace.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Result<T> {

    private String message;

    private String code;

    private T data;

    private List<T> dataList;

    private Integer limit;

    private Integer page;

    private Boolean success;

    public void buildSuccessInfo(String message, String code, T data) {
        this.setMessage(message);
        this.setCode(code);
        this.setData(data);
        this.setSuccess(true);
    }

    public void buildSuccessInfo(String message, String code, List<T> dataList) {
        this.setMessage(message);
        this.setCode(code);
        this.setDataList(dataList);
        this.setSuccess(true);
    }

    public void buildErrorInfo(String message, String code) {
        this.setMessage(message);
        this.setCode(code);
        this.setData(data);
        this.setSuccess(false);
    }
}
