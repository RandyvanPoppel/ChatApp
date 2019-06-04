package sockets;

import com.google.gson.Gson;
import models.Message;
import models.User;
import services.MessageService;
import services.UserService;

import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/chat")
public class ChatSocket {

    @Inject
    MessageService messageService;

    @Inject
    UserService userService;

    private Gson gson = new Gson();

    private Session session;
    private static Set<ChatSocket> chatClients = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session) throws IOException {
        // Get session and WebSocket connection
        System.out.println("Session connected: " + session.getId());
        this.session = session;
        chatClients.add(this);
    }

    @OnMessage
    public void onMessage(Session session, String messageString) throws IOException {
        Map map = gson.fromJson(messageString, Map.class);
        User authenticatedUser = userService.checkIfUserAuthenticated(map.get("token").toString());
        // Handle new messages
        if (authenticatedUser != null) {
            Message message = messageService.addMessage(map.get("message").toString(), authenticatedUser);
            try {
                broadcast(gson.toJson(message));
            } catch (EncodeException e) {
                e.printStackTrace();
            }
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        // WebSocket connection closes
        this.session = session;
        chatClients.remove(this);
        System.out.println("Session disconnected: " + session.getId());
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
        System.out.println(throwable.getMessage());
    }

    private static void broadcast(String message)
            throws IOException, EncodeException {

        chatClients.forEach(endpoint -> {
            synchronized (endpoint) {
                try {
                    endpoint.session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
