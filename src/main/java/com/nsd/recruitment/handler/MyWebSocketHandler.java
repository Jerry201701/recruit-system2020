package com.nsd.recruitment.handler;

import com.alibaba.fastjson.JSON;
import com.nsd.recruitment.domain.ChatMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelMatcher;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.HashMap;
import java.util.Map;

/**
 * MyWebSocketHandler
 * WebSocket处理器，处理websocket连接相关
 * @author zhengkai.blog.csdn.net
 * @date 2019-06-12
 */


public class MyWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("与客户端建立连接，通道开启！");

        //添加到channelGroup通道组
        MyChannelHandlerPool.channelGroup.add(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("与客户端断开连接，通道关闭！");

        //添加到channelGroup 通道组
        MyChannelHandlerPool.channelGroup.remove(ctx.channel());

        WebSocketCacheMap.deleteWsByValue(ctx.channel());
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        //首次连接是FullHttpRequest
        if (null != msg && msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;
            String uri = request.uri();
            Map<String,String> paramMap=getUrlParams(uri);
            System.out.println("接收到的参数是："+ JSON.toJSONString(paramMap));
            String id =paramMap.get("id");
            WebSocketCacheMap.saveWs(id,ctx.channel());

            if(uri.contains("?")){
                String newUri=uri.substring(0,uri.indexOf("?"));
                System.out.println(newUri);
                request.setUri(newUri);
            }

        }else if(msg instanceof TextWebSocketFrame){
            //正常的TEXT消息类型
            TextWebSocketFrame frame=(TextWebSocketFrame)msg;
            System.out.println("客户端收到服务器数据：" +frame.text());

            ChatMessage chatMessage= JSON.parseObject(frame.text(), ChatMessage.class);
            Channel companyChannel=WebSocketCacheMap.getByToken("C".concat(chatMessage.getCompanyId().toString()));
            Channel applicantChannel=WebSocketCacheMap.getByToken("A".concat(chatMessage.getApplicantId().toString()));
            chatMessage.setUnread(true);
            TextWebSocketFrame tws1 = new TextWebSocketFrame(JSON.toJSONString(chatMessage));
            //招聘者向求职者发消息
            if (chatMessage.getMessageType()==3){
                companyChannel.writeAndFlush(tws1);
                if (applicantChannel!=null){
                chatMessage.setUnread(false);
                TextWebSocketFrame tws2 = new TextWebSocketFrame(JSON.toJSONString(chatMessage));
                applicantChannel.writeAndFlush(tws2);
                }
            }

            //求职者向招聘者发信息
            if (chatMessage.getMessageType()==4){
                applicantChannel.writeAndFlush(tws1);
                if (companyChannel!=null){
                    chatMessage.setUnread(false);
                    TextWebSocketFrame tws3 = new TextWebSocketFrame(JSON.toJSONString(chatMessage));
                    companyChannel.writeAndFlush(tws3);
                }
            }

         //   ctx.channel().writeAndFlush(tws);
        }
        super.channelRead(ctx, msg);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {

    }

    private void sendAllMessage(String message ){
        //收到信息后，群发给所有channel
        MyChannelHandlerPool.channelGroup.writeAndFlush(new TextWebSocketFrame(message), new ChannelMatcher() {
            @Override
            public boolean matches(Channel channel) {
                return true;
            }
        });

    }



    private static Map<String, String> getUrlParams(String url){
        Map<String,String> map = new HashMap<>();
        url = url.replace("?",";");
        if (!url.contains(";")){
            return map;
        }
        if (url.split(";").length > 0){
            String[] arr = url.split(";")[1].split("&");
            for (String s : arr){
                String key = s.split("=")[0];
                String value = s.split("=")[1];
                map.put(key,value);
            }
            return  map;

        }else{
            return map;
        }
    }

}