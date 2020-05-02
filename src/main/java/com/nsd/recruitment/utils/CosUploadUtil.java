package com.nsd.recruitment.utils;

import com.alibaba.fastjson.JSON;
import com.nsd.recruitment.vo.CosSecretVo;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import com.tencent.cloud.CosStsClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class CosUploadUtil {

    private static  final String SECRET_ID="xxxxxxxxxxxxxxxxx";
    private static  final String SECRET_KEY="xxxxxxxxxxxxxxx";
    private static  final Integer DURATION_SECONDS=1800;
    private static  final String REGION_NAME="xxxxxxxxxxxx";
    private static  final String BUCKET_NAME="xxxxxxxxxxxxxxx";
    private static  final String ALLOW_PREFIX="*";


    /**
     * 生成临时密钥
     * @return
     */

    public  static JSONObject createTempSecret(){


        TreeMap<String, Object> config = new TreeMap<String, Object>();

        try {
            // 替换为您的 SecretId
            config.put("SecretId", SECRET_ID);
            // 替换为您的 SecretKey
            config.put("SecretKey", SECRET_KEY);

            // 临时密钥有效时长，单位是秒，默认1800秒，最长可设定有效期为7200秒
            config.put("durationSeconds", DURATION_SECONDS);

            // 换成您的 bucket
            config.put("bucket", BUCKET_NAME);
            // 换成 bucket 所在地区
            config.put("region", REGION_NAME);

            // 这里改成允许的路径前缀，可以根据自己网站的用户登录态判断允许上传的具体路径，例子：a.jpg 或者 a/* 或者 * 。
            // 如果填写了“*”，将允许用户访问所有资源；除非业务需要，否则请按照最小权限原则授予用户相应的访问权限范围。
            config.put("allowPrefix", ALLOW_PREFIX);

            // 密钥的权限列表。简单上传、表单上传和分片上传需要以下的权限，其他权限列表请看 https://cloud.tencent.com/document/product/436/31923
            String[] allowActions = new String[] {
                    // 简单上传
                    "name/cos:PutObject",
                    // 表单上传、小程序上传
                    "name/cos:PostObject",
                    // 分片上传
                    "name/cos:InitiateMultipartUpload",
                    "name/cos:ListMultipartUploads",
                    "name/cos:ListParts",
                    "name/cos:UploadPart",
                    "name/cos:CompleteMultipartUpload"
            };
            config.put("allowActions", allowActions);

            JSONObject credential = CosStsClient.getCredential(config);

            //成功返回临时密钥信息，如下打印密钥信息
        //    System.out.println(credential);
            return credential;
        } catch (Exception e) {
            //失败抛出异常
            throw new IllegalArgumentException("no valid secret !");
        }

    }

    /**
     * 向cos上传图片
     * @param uploadFile
     * @param key
     * @return
     */

    public static String uploadPicture(File uploadFile, String key){
        COSCredentials cred = new BasicCOSCredentials(SECRET_ID, SECRET_KEY);
// 2 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
// clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region(REGION_NAME);
        ClientConfig clientConfig = new ClientConfig(region);
        COSClient cosClient = new COSClient(cred, clientConfig);

        try {
            // 指定要上传到的存储桶
            String bucketName = BUCKET_NAME;
            // 指定要上传到 COS 上对象键
            // String key = "exampleobject";
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, uploadFile);
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            System.out.println(putObjectResult.getContentMd5());
        } catch (CosServiceException serverException) {
            serverException.printStackTrace();
        } catch (CosClientException clientException) {
            clientException.printStackTrace();
        }finally {
            cosClient.shutdown();
        }

        return null;
    }

    public static String  generateFileName(){
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        String key ="test/".concat(uuid);
        return key;

    }
    public static File multipartfileToFile(MultipartFile multipartFile){

        // 获取文件名
        String fileName = multipartFile.getOriginalFilename();
        // 获取文件后缀
        String suffix=fileName.substring(fileName.lastIndexOf("."));
        // 用uuid作为文件名，防止生成的临时文件重复
        // MultipartFile to File
        try {
            final File tempFile = File.createTempFile(generateFileName(), suffix);
            multipartFile.transferTo(tempFile);
            tempFile.delete();

        } catch (IOException e) {
            e.printStackTrace();
        }


        return  null;
    }

    public static Map<String,Object> JsonToMap(){
        Map<String,Object> map=new HashMap<>();
        JSONObject json= CosUploadUtil.createTempSecret();
        JSONObject jsonObject=json.getJSONObject("credentials");
        map.put("tmpSecretId",jsonObject.getString("tmpSecretId"));
        map.put("tmpSecretKey",jsonObject.getString("tmpSecretKey"));
        map.put("sessionToken",jsonObject.getString("sessionToken"));
        map.put("expiredTime",json.getLong("expiredTime"));
        map.put("StartTime",System.currentTimeMillis()/1000);


        return map;
    }
    public static  JSONObject cosSecret(){

//        JSONObject jsonObject=CosUploadUtil.createTempSecret().getJSONObject("credentials");
//        CosSecretVo cosSecretVo=JSON.parseObject(jsonObject.toString(), CosSecretVo.class);
//        cosSecretVo.setStartTime(System.currentTimeMillis()/100);
            JSONObject jsonObject=CosUploadUtil.createTempSecret();
        return jsonObject;
    }

    // 从输入流进行读取并上传到COS
    public static String SimpleUploadFileFromStream(InputStream input) throws IOException {
        String key= "wei/code/".concat(UUID.randomUUID().toString().replace("-", "").toLowerCase()).concat(".jpg");
        COSCredentials cred = new BasicCOSCredentials(SECRET_ID,SECRET_KEY);
        Region region = new Region(REGION_NAME);
        ClientConfig clientConfig = new ClientConfig(region);
        COSClient cosClient = new COSClient(cred, clientConfig);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        // 从输入流上传必须制定content length, 否则http客户端可能会缓存所有数据，存在内存OOM的情况
       // objectMetadata.setContentLength(Integer.valueOf(input.available()).longValue());
        // 默认下载时根据cos路径key的后缀返回响应的contenttype, 上传时设置contenttype会覆盖默认值
        objectMetadata.setContentType("image/jpg");
        PutObjectRequest putObjectRequest =
                new PutObjectRequest(BUCKET_NAME, key, input, objectMetadata);
        // 设置存储类型, 默认是标准(Standard), 低频(standard_ia)
        putObjectRequest.setStorageClass(StorageClass.Standard);
        try {
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            System.out.println(putObjectResult.getContentMd5());
            // 关闭客户端
            cosClient.shutdown();
            return "https://"+BUCKET_NAME+".cos.ap-chengdu.myqcloud.com/"+key;
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        }

        // 关闭客户端
        cosClient.shutdown();
        return null;
    }


//public static  downFile(){
//
//    try{
//        // 指定对象所在的存储桶
//        String bucketName = "examplebucket-1250000000";
//        // 指定对象在 COS 上的对象键
//        String key = "exampleobject";
//        // 指定要下载到的本地路径
//
//        File downFile = new File("exampleobject");
//        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
//        ObjectMetadata downObjectMeta = cosClient.getObject(getObjectRequest, downFile);
//    } catch (CosServiceException serverException) {
//        serverException.printStackTrace();
//    } catch (CosClientException clientException) {
//        clientException.printStackTrace();
//    }

//}


    /**
     * 删除
     *
     * @param files
     */
    private void deleteFile(File... files) {
        for (File file : files) {
            if (file.exists()) {
                file.delete();
            }
        }
    }




    public  static String saveCode(String token,String scene){
        Map<String,Object> map=new HashMap<>();
        map.put("page","pages/index/index");
        map.put("scene",scene);
        map.put("width",600);
        String url="https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=".concat(token);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            StringEntity entity = new StringEntity(JSON.toJSONString(map), ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            response = httpClient.execute(httpPost);
            InputStream stream=response.getEntity().getContent();
            String result= CosUploadUtil.SimpleUploadFileFromStream(stream);
            System.out.println(result);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        return null;
    }




}
