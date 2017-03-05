/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import model.Message;
import model.Notification;

/**
 *
 * @author N5537
 */
@ServerEndpoint("/endpoint")
public class WebSocket {
    
    private static final Set<Session> SESSIONS = ConcurrentHashMap.newKeySet();
    
    @OnOpen
    public void onOpen(Session session) {
        SESSIONS.add(session);
    }

    @OnClose
    public void onClose(Session session) {
        SESSIONS.remove(session);
    }
    
    public static void sendAll(Notification noti) {
        synchronized (SESSIONS) {
            for (Session session : SESSIONS) {
                if (session.isOpen()) {
                    session.getAsyncRemote().sendObject(noti.getUser().getUsername());
                }
            }
        }
    }
    
    public static void messAll(Message mess) {
        synchronized (SESSIONS) {
            for (Session session : SESSIONS) {
                if (session.isOpen()) {
                    session.getAsyncRemote().sendObject(mess.getMessageID());
                }
            }
        }
    }
}
