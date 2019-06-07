package com.leo.springboot.websocket;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/websocket/{uid}")
public class MyWebSocket {

    @OnOpen
    public void onOpen(Session session, @PathParam(value = "uid")String uid) throws IOException {
        System.out.println("websocket�Ѿ�����" + session);
        // ���ͻ�����Ӧ����ӭ��½�����ӣ�ϵͳ
        session.getBasicRemote().sendText(uid+"����ã���ӭ��½ϵͳ");
    }

    @OnClose
    public void onClose(Session session){
        System.out.println("websocket�Ѿ��ر�" + session);
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println("�յ��ͻ��˷�������Ϣ --> " + message);

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //���ͻ���һ������
        session.getBasicRemote().sendText("��Ϣ���յ�");
    }

}
