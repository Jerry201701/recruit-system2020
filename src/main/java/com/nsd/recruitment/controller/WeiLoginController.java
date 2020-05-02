package com.nsd.recruitment.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nsd.recruitment.common.HttpResult;
import com.nsd.recruitment.domain.ConversationMessage;
import com.nsd.recruitment.domain.ResultVo;
import com.nsd.recruitment.domain.UserInfo;
import com.nsd.recruitment.domain.query.DecodePhoneInfo;
import com.nsd.recruitment.service.UserInfoService;
import com.nsd.recruitment.utils.DecryptionUtil;
import com.nsd.recruitment.utils.HttpClientUtil;
import com.nsd.recruitment.utils.OtherUtil;
import com.nsd.recruitment.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/wei")
public class WeiLoginController {

    Logger logger= LoggerFactory.getLogger(WeiLoginController.class);
    // 请求的网址
    private static final String WX_LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session";
    // 招聘者的appid
    private static final String COMPANY_APPID = "xxxxxxxxxxxx";
    //求职者的appid
    private static final String EMPLOYEE_APPID="xxxxxxxxxxxxx";
    // 招聘者密匙
    private static final String COMPANY_SECRET = "xxxxxxxxxxxxxx";
    //求职者密钥
    private static final String EMPLOYEE_SECRET = "xxxxxxxxxxxxxxx";
    // 固定参数
    private static final String WX_LOGIN_GRANT_TYPE = "xxxxxxxxxxxxxxx";

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private MongoTemplate mongoTemplate;
//    @Resource
//    private RedisUtil redisUtil;

    /*
微信授权登陆
 */
    @GetMapping(value = "/employee")
    public HttpResult getEmployeeOpenid(@RequestParam("code") String code) {



        return HttpResult.ok(getOpenidByCode(code,EMPLOYEE_APPID,EMPLOYEE_SECRET));


    }

    @GetMapping(value = "/company")
    public HttpResult getCompanyOpenid(@RequestParam("code") String code) {

        System.out.println("获取openid");



        // 配置请求参数

//        Map<String, String> param = new HashMap<>();
//        param.put("appid", COMPANY_APPID);
//        param.put("secret", WX_LOGIN_SECRET);
//        param.put("js_code", code);
//        param.put("grant_type", WX_LOGIN_GRANT_TYPE);
//        // 发送请求
//
//        String wxResult = HttpClientUtil.doGet(WX_LOGIN_URL, param);
//        JSONObject jsonObject = JSONObject.parseObject(wxResult);
//        String sessionKey = jsonObject.get("session_key").toString();
//
//        String openid = jsonObject.get("openid").toString();
//
//        // 封装返回小程序
//        Map<String, String> result = new HashMap<>();
//        result.put("sessionKey", sessionKey);
//        result.put("openid", openid);
//

        return HttpResult.ok(getOpenidByCode(code,COMPANY_APPID,COMPANY_SECRET));


    }
    private Map<String,String> getOpenidByCode(String code,String appid,String secret){

        Map<String, String> param = new HashMap<>();
        param.put("appid", appid);
        param.put("secret", secret);
        param.put("js_code", code);
        param.put("grant_type", WX_LOGIN_GRANT_TYPE);
        // 发送请求

        String wxResult = HttpClientUtil.doGet(WX_LOGIN_URL, param);
        JSONObject jsonObject = JSONObject.parseObject(wxResult);
        String sessionKey = jsonObject.get("session_key").toString();

        String openid = jsonObject.get("openid").toString();

        // 封装返回小程序
        Map<String, String> result = new HashMap<>();
        result.put("sessionKey", sessionKey);
        result.put("openid", openid);

        return result;

    }




    @PostMapping(value = "/register")
    public HttpResult registerNewUser(@RequestBody UserInfo userInfo) {
        userInfo.setFlag(true);

        return HttpResult.ok(userInfoService.saveUserInfo(userInfo));
    }


    /*
    解密电话号码
     */
    @Transactional
    @PostMapping(value = "/decode/phone")
    public HttpResult decodeWxAppPhone(@RequestBody DecodePhoneInfo decodePhoneInfo) {
        try {
        //    System.out.println("解密手机号");
        //    System.out.println("redis过期时间："+redisUtil.getExpire("sessionKey"));
          //  String key=redisUtil.get("sessionKey").toString();
                String phoneInfo = DecryptionUtil.getPhoneNumber(decodePhoneInfo.getEncryptedData(),decodePhoneInfo.getSessionKey(), decodePhoneInfo.getIv());
                JSONObject info=JSON.parseObject(phoneInfo);
                UserInfo userInfo=new UserInfo();
                userInfo.setLoginPhone(info.getString("phoneNumber"));
                userInfo.setOpenid(decodePhoneInfo.getOpenid());
                userInfo.setFlag(true);
                userInfo.setLoginType(decodePhoneInfo.getType());
                return HttpResult.ok(userInfoService.saveUserInfo(userInfo));

        } catch (Exception e) {
            log.error("微信小程序手机号码解密异常，信息如下:", e);
        }
            return null;
    }
    /*
    获取用户unionid
     */
//    @PostMapping(value = "/union/id")
//    public ResultVo getUnionId(@RequestBody DecodePhoneInfo decodePhoneInfo){
//        ResultVo code= weiLogin(decodePhoneInfo.getCode());
//        String key=redisUtil.get("sessionKey").toString();
//        String phoneInfo = DecryptionUtil.getPhoneNumber(decodePhoneInfo.getEncryptedData(),key, decodePhoneInfo.getIv());
//        JSONObject info=JSON.parseObject(phoneInfo);
//        System.out.println(info);
//        ResultVo resultVo=new ResultVo();
//        resultVo.setData(info);
//
//        return resultVo;
//    }


    @PostMapping(value = "/mongo/add")
    public HttpResult addToMongo(@RequestBody ConversationMessage conversationMessage){
      //  Date present=new Date();
       // conversationMessage.setCreateTime(present);
       // conversationMessage.setCreateTime(new Timestamp(System.currentTimeMillis()));
        mongoTemplate.insert(conversationMessage);

        return HttpResult.ok();
    }
    @GetMapping("/mongo/find")
    public HttpResult findMessage(){
      //  ConversationMessage conversationMessage=new ConversationMessage();

       List<ConversationMessage>list= mongoTemplate.findAll(ConversationMessage.class);
       return HttpResult.ok(list);

    }

}
