package com.chatApp.wehelpsu.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.chatApp.wehelpsu.client.ChatMessagesService;
import com.chatApp.wehelpsu.shared.ChatMessage;

import java.util.ArrayList;
import java.util.List;


public class ChatMessagesServiceImpl extends RemoteServiceServlet implements ChatMessagesService {
    private List<ChatMessage> messages = new ArrayList<ChatMessage>();

    @Override
    public ChatMessage[] getMessages() {
        return messages.toArray(new ChatMessage[messages.size()]);
    }

    @Override
    public ChatMessage[] addMessage(final ChatMessage msg) {
        messages.add(msg);
        return messages.toArray(new ChatMessage[messages.size()]);
    }
}
