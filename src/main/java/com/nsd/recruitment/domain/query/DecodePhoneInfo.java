package com.nsd.recruitment.domain.query;

import lombok.Data;

@Data
public class DecodePhoneInfo {
    private String encryptedData;
    private String iv;
    private String sessionKey;
    private String code;
    private String openid;
    /*
    1,招聘者
    2，求职者
     */
    private Integer type;

}
