package com.nsd.recruitment.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class JobUtil {
    public static String getCompanyCode() {

       return  new JobUtil().getUUID().concat("company");
    }

    public static String getHunterCode() {
      return new JobUtil().getUUID().concat("hunter");
    }

    public  String getUUID() {
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();
        //去掉随机ID的短横线
        id = id.replace("-", "");
       // System.out.println(id);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String dateTime = LocalDateTime.now(ZoneOffset.of("+8")).format(formatter);
      //  System.out.println(dateTime);
        id=id.concat(dateTime);
        return id;
    }

}
