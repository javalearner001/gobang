package com.sun.gobang.socket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.net.SocketException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sunkai
 * @since 2021/11/3 3:35 下午
 */
@Service
@ServerEndpoint(value = "/message")
@Slf4j
public class WebSocket {

    private static Map<String,WebSocket> webSocketMap = new ConcurrentHashMap<>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    @OnOpen
    public void onOpen(Session session) throws SocketException {
        this.session = session;
        webSocketMap.put(this.session.getId(),this);
        log.info("[websocket] 有新的连接，总数:{}",webSocketMap.size());
    }

    @OnClose
    public void onClose(){
        String id = this.session.getId();
        if (id != null){
            webSocketMap.remove(id);
            log.info("[websocket] 连接断开，总数:{}",webSocketMap.size());
        }
    }

    @OnMessage
    public void onMessage(String message){
        if (!message.equals("ping")){
            log.info("[webSocket]收到客户端发送的消息，message:{}",message);
            sendMessage(message);
        }
    }

    /**
     * 发送消息 广播
     * @param message
     * @return 全部都发送一遍
     */
    public void sendMessage(String message){
        for (WebSocket webSocket : webSocketMap.values()) {
            webSocket.session.getAsyncRemote().sendText(message);
        }
        log.info("【wesocket】广播消息,message={}", message);

    }

    /**
     * 此为单点消息 (发送对象)
     */
    public void sendObjMessage(String shopId, Object message) {
        this.session.getAsyncRemote().sendObject(message);
    }

}
