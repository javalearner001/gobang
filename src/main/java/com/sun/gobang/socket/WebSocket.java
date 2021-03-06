package com.sun.gobang.socket;

import com.alibaba.fastjson.JSON;
import com.sun.gobang.chess.MatchOpponentService;
import com.sun.gobang.chess.PlayChessService;
import com.sun.gobang.entity.SocketMessage;
import com.sun.gobang.entity.enumc.MessageTypeEnum;
import com.sun.gobang.entity.response.MessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.net.SocketException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sunkai
 * @since 2021/11/3 3:35 下午
 */
@Service
@ServerEndpoint(value = "/message/{userId}")
@Slf4j
public class WebSocket implements ApplicationContextAware {

    public static final Map<String, WebSocket> webSocketMap = new ConcurrentHashMap<>();

    private static ApplicationContext applicationContext;
    private MatchOpponentService matchOpponentService;
    private PlayChessService playChessService;

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) throws SocketException {
        if (webSocketMap.containsKey(userId)) {
            log.info("此连接已存在！");
            return;
        }
        this.session = session;
        webSocketMap.put(userId, this);
        log.info("[websocket] 有新的连接，总数:{}", webSocketMap.size());

        MessageResponse response = new MessageResponse("恭喜您成功连接上WebSocket-->当前在线人数为：" + webSocketMap.size(),MessageTypeEnum.BROAD_CAST.getCode());
        sendObjMessage(response);
        //service 配置
        matchOpponentService = applicationContext.getBean(MatchOpponentService.class);
        playChessService = applicationContext.getBean(PlayChessService.class);
    }

    @OnClose
    public void onClose() {
        String id = this.session.getId();
        if (id != null) {
            webSocketMap.remove(id);
            log.info("[websocket] 连接断开，总数:{}", webSocketMap.size());
        }
    }

    @OnMessage
    public void onMessage(String message) {
        SocketMessage socketMessage = JSON.parseObject(message, SocketMessage.class);
        log.info("[webSocket]收到客户端发送的消息，socketMessage:{}", JSON.toJSONString(socketMessage));

        switch (socketMessage.getMessageType()) {
            case 100:
                MessageResponse response = new MessageResponse(socketMessage.getText(), MessageTypeEnum.BROAD_CAST.getCode());
                sendMessage(response);
                break;
            case 1001:
                matchOpponent(socketMessage.getUserId());
                break;
            case 1002:
                playChess(socketMessage);
                break;
        }

    }

    /**
     * 下棋
     * @param socketMessage
     */
    private void playChess(SocketMessage socketMessage) {

        playChessService.lowChess(socketMessage);
    }

    /**
     * 发送消息 广播
     *
     * @param messageResponse
     * @return 全部都发送一遍
     */
    public void sendMessage(MessageResponse messageResponse) {
        for (WebSocket webSocket : webSocketMap.values()) {
            webSocket.session.getAsyncRemote().sendText(JSON.toJSONString(messageResponse));
        }
        log.info("【wesocket】广播消息,message={}", JSON.toJSONString(messageResponse));
    }

    /**
     * 此为单点消息 (发送对象)
     */
    public void sendObjMessage(MessageResponse message) {
        this.session.getAsyncRemote().sendText(JSON.toJSONString(message));
    }

    /**
     * 匹配对手 加入队列
     */
    public void matchOpponent(String userId) {
        SessionQueue sessionQueue = SessionQueue.getSessionQueue();

        if (!sessionQueue.contains(userId)) {
            try {
                sessionQueue.produce(userId);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        MessageResponse messageResponse = new MessageResponse("请稍等，正在为您匹配对手", MessageTypeEnum.BROAD_CAST.getCode());
        sendObjMessage(messageResponse);
        log.info("队列的数据为:{}", JSON.toJSONString(sessionQueue));
        //添加之后，判断队列长度，>=2 ,要对队列进行出队列，创建房间
        if (sessionQueue.size() >= 2) {
            String userIdOne = sessionQueue.consume();
            String userIdTwo = sessionQueue.consume();

            matchOpponentService.createChessRoom(userIdOne, userIdTwo);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        WebSocket.applicationContext = applicationContext;
    }
}
