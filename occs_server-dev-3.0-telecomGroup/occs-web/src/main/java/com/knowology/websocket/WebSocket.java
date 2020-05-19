package com.knowology.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: websoket
 * @author: Conan
 * @create: 2019-03-08 14:41
 **/
@ServerEndpoint(value = "/websocket/{clientId}")
@Component
public class WebSocket {
    private static final Logger logger = LoggerFactory.getLogger(WebSocket.class);

    public WebSocket() {
        System.out.println("实例化WebSocket:" + this.hashCode());
    }

    private static ConcurrentHashMap<String, WebSocket> webSocketMap = new ConcurrentHashMap<>();

    //客户端session
    private Session session;
    //客户端session id
    private String sid;

    /**
     * 开启websocket时调用
     * @param session
     * @param config
     */
    @OnOpen
    public void onOpen(@PathParam("clientId") String clientId, Session session, EndpointConfig config) {
        this.session = session;
        // TODO: 2019/7/31 这里也可以用websocket的hashcode？ 
        this.sid = clientId;
        logger.info("websocket连接开启,client is:" + sid);
        webSocketMap.put(sid, this);
    }

    /**
     * 关闭websocket连接时调用
     */
    @OnClose
    public void onClose() {
        webSocketMap.remove(this);
        logger.info("连接关闭");
    }

    /**
     * 接收客户端消息时调用
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("客户端消息:" + message);
    }

    /**
     * 异常时调用
     * @param session
     * @param e
     */
    @OnError
    public void onError(Session session, Throwable e) {
        logger.error("websocket通信出现异常:", e);
    }

    /**
     * 发送消息
     * @param message
     */
    public void sendMessage(String message) {
        if (this.session != null) {
            try {
                this.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                logger.error("WebSocket发送消息异常", e);
            }
        }
    }

    /**
     * 发送对象
     * @param data
     */
    public void sendObject(Object data) {
        if (this.session != null) {
            try {
                this.session.getBasicRemote().sendObject(data);
            } catch (IOException e) {
                logger.error("WebSocket发送消息异常", e);
            } catch (EncodeException e) {
                logger.error("WebSocket发送消息异常", e);
            }
        }
    }

    /**
     * 推送消息内容到指定客户端
     * @param message
     * @param sid
     */
    public static void pushInfo(String message, String sid) {
        if (sid != null) {
            WebSocket webSocket = webSocketMap.get(sid);
            if (webSocket != null) {
                logger.debug("发送通话内容:"+message);
                webSocket.sendMessage(message);
            }
        }
    }
}
