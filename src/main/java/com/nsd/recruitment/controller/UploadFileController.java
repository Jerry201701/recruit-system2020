package com.nsd.recruitment.controller;

import com.nsd.recruitment.common.HttpResult;
import com.nsd.recruitment.utils.CosUploadUtil;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.UUID;

@RestController
@RequestMapping(value = "/pic")
public class UploadFileController {


    @RequestMapping(value = "/upload/logo", method = RequestMethod.POST)
    public String file(@RequestParam("logo") MultipartFile file) {

        String filePath;
        String path="E:\\nsd\\images\\logo\\";
        //  String path="/usr/local/nginx/html/images/pic/goods";
        if (!file.isEmpty()) {
            try {
                int begin = file.getOriginalFilename().indexOf(".");
                int last = file.getOriginalFilename().length();
                //获得文件后缀名
                String a = file.getOriginalFilename().substring(begin, last);
                String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
                uuid += a;
                String paths= path +  "/";
                //   String paths= path ;
                // 判断文件是否为空
                File file1 = new File(paths);
                if (!file1.exists()) {
                    file1.mkdirs();//创建
                }
                // 文件保存路径
                filePath = paths + uuid;
                // 转存文件
                file.transferTo(new File(filePath));
              //  System.out.println(filePath);
                // String p="http://www.scsflr.com/images/pic/goods/"+uuid;
                return filePath;
            } catch (Exception e) {
                e.printStackTrace();
                return "文件保存失败";
            }
        }else
            return "文件上传失败";
//        try {
//            filePath = java.net.URLEncoder.encode(filePath, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        //  System.out.println(filePath);
        // return  filePath;

    }

    @GetMapping(value = "/create/cos/secret")
    public JSONObject createSecret(){
//        Map<String,Object> map=new HashMap<>();
//        JSONObject json= CosUploadUtil.createTempSecret();
//        JSONObject jsonObject=json.getJSONObject("credentials");
//        map.put("tmpSecretId",jsonObject.getString("tmpSecretId"));
//        map.put("tmpSecretKey",jsonObject.getString("tmpSecretKey"));
//        map.put("sessionToken",jsonObject.getString("sessionToken"));
//        map.put("expiredTime",json.getLong("expiredTime"));


        return CosUploadUtil.cosSecret();

    }







}
