package com.example.springwebsocket.spring.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import org.springframework.web.socket.sockjs.transport.session.WebSocketServerSockJsSession;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class WebSocketHandler extends AbstractWebSocketHandler {

    public static final String KEY_TYPE = "keyType";
    /**
     * 存储sessionId和webSocketSession
     * 需要注意的是，webSocketSession没有提供无参构造，不能进行序列化，也就不能通过redis存储
     * 在分布式系统中，要想别的办法实现webSocketSession共享
     */
    private static Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();
    private static Map<String, String> keyTypeMap = new ConcurrentHashMap<>();

    /**
     * 获取sessionId
     */
    private String getSessionId(WebSocketSession session) {
        if (session instanceof WebSocketServerSockJsSession) {
            // sock js 连接
            try {
                return  ((WebSocketSession) FieldUtils.readField(session, "webSocketSession", true)).getId();
            } catch (IllegalAccessException e) {
                throw new RuntimeException("get sessionId error");
            }
        }
        return session.getId();
    }

    /**
     * webSocket连接创建后调用
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        // 获取参数
        String keyType = String.valueOf(session.getAttributes().get(KEY_TYPE));
        String sessionId = getSessionId(session);
        keyTypeMap.put(keyType, getSessionId(session));
        sessionMap.put(sessionId, session);
    }

    /**
     * 接收到消息会调用
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        if (message instanceof TextMessage) {
            sendMessage(session.getAttributes().get(KEY_TYPE).toString()," session   " + session.getId());
        } else if (message instanceof BinaryMessage) {

        } else if (message instanceof PongMessage) {

        } else {
            System.out.println("Unexpected WebSocket message type: " + message);
        }
    }

    /**
     * 连接出错会调用
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        sessionMap.remove(getSessionId(session));
    }

    /**
     * 连接关闭会调用
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessionMap.remove(getSessionId(session));
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 后端发送消息
     */
    public void sendMessage(String keyType, String message) {
        String sessionId = keyTypeMap.get(keyType);
        WebSocketSession session = sessionMap.get(sessionId);
        try {
            session.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
