package com.nsd.recruitment.vo;

import lombok.Data;

@Data
public class CosSecretVo {
    private String tmpSecretId;
    private String tmpSecretKey;
    private String sessionToken;
    private String expiredTime;
    private Long startTime;
}
