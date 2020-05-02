package com.nsd.recruitment.handler;

import io.netty.channel.Channel;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketCacheMap {
    /**
     * 存储用户标识与链接实例
     */
    private final static Map<String, Channel> channelByTokenMap = new ConcurrentHashMap<>();

    /**
     * 存储链接地址与用户标识
     */
    private final static Map<String,String> addressByTokenMap = new ConcurrentHashMap<>();


    /**
     * 存储链接
     * @param token {@link String} 用户标签
     * @param channel {@link Channel} 链接实例
     */
    public static void saveWs(String token,Channel channel){
        channelByTokenMap.put(token,channel);
        System.out.println("管道信息："+channelByTokenMap.toString());

    }

    /**
     * 存储登录信息
     * @param address 登录地址
     * @param token 用户标签
     */
    public static void saveAd(String address,String token){
        addressByTokenMap.put(address, token);
    }

    /**
     * 获取链接数据
     * @param token {@link String} 用户标识
     * @return {@link Channel} 链接实例
     */
    public static Channel getByToken(String token){

        return channelByTokenMap.get(token);
    }

    /**
     * 获取对应token标签
     * @param address {@link String} 链接地址
     * @return {@link String}
     */
    public static String getByAddress(String address){
        return addressByTokenMap.get(address);
    }



    /**
     * 删除链接地址
     * @param address
     */
    public static void deleteAd(String address){
        addressByTokenMap.remove(address);
    }

    /**
     * 获取链接数
     * @return {@link Integer} 链接数
     */
    public static Integer getSize(){

        return channelByTokenMap.size();
    }

    /**
     * 判断是否存在链接账号
     * @param token {@link String} 用户标识
     * @return {@link Boolean} 是否存在
     */
    public static boolean hasToken(String token){

        return channelByTokenMap.containsKey(token);
    }

    /**
     * 获取在线用户标签列表
     * @return {@link Set} 标识列表
     */
    public static Set<String> getTokenList(){

        Set keys = channelByTokenMap.keySet();
        return keys;
    }

    /**
     * 删除链接数据
     * @param token {@link String} 用户标识
     */
    public static void deleteWs(String token){
        try {
            channelByTokenMap.remove(token);


        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }
    public static  void  deleteWsByValue(Channel channel){

        Collection<Channel> col = channelByTokenMap.values();
        while(true == col.contains(channel)) {
            col.remove(channel);
        }
        System.out.println(channelByTokenMap.toString());

    }


}
