package com.nsd.recruitment.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultVo implements Serializable {
    private static final long serialVersionUID = -4965133330274129519L;
    private static  final Integer SUCCESS=200;
    private static  final Integer ERROR=500;
    private Integer code;
    private Object data;
    private String msg;

    public ResultVo(Integer code, Object data) {
        this.code = code;
        this.data = data;
    }
}
