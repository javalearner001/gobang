package com.sun.gobang.socket;

import com.alibaba.fastjson.JSON;
import com.sun.gobang.entity.InitParam;
import com.sun.gobang.entity.SocketMessage;
import com.sun.gobang.entity.response.MessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.net.SocketException;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sunkai
 * @since 2021/11/3 3:35 下午
 */
@Service
@ServerEndpoint(value = "/message/{userId}")
@Slf4j
public class WebSocket {

    ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue(1000);

    private static final Map<String,WebSocket> webSocketMap = new ConcurrentHashMap<>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) throws SocketException {
        if (webSocketMap.containsKey(userId)){
            log.info("此连接已存在！");
            return;
        }
        this.session = session;
        webSocketMap.put(userId,this);
        log.info("[websocket] 有新的连接，总数:{}",webSocketMap.size());

        this.session.getAsyncRemote().sendText("恭喜您成功连接上WebSocket-->当前在线人数为："+ webSocketMap.size());
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
        SocketMessage socketMessage = JSON.parseObject(message, SocketMessage.class);
        log.info("[webSocket]收到客户端发送的消息，socketMessage:{}",JSON.toJSONString(socketMessage));

        switch (socketMessage.getMessageType()){
            case 100 :
                sendMessage(socketMessage.getText());
                break;
            case 1001:
                matchOpponent(socketMessage.getUserId());
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
    public void sendObjMessage(String userId, MessageResponse message) {
        WebSocket webSocket = webSocketMap.get(userId);
        if (webSocket != null){
            webSocket.session.getAsyncRemote().sendText(message.getMessage());
        }
    }

    /**
     * 匹配对手 加入队列
     */
    public void matchOpponent(String userId){
        blockingQueue.add(userId);

        MessageResponse messageResponse = new MessageResponse("请稍等，正在为您匹配对手");
        sendObjMessage(userId,messageResponse);
        log.info("队列的数据为:{}",JSON.toJSONString(blockingQueue));
    }

}
