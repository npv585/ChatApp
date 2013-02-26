package com.chatApp.wehelpsu.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.chatApp.wehelpsu.shared.ChatMessage;


@RemoteServiceRelativePath("messages")
public interface ChatMessagesService extends RemoteService {
    ChatMessage[] getMessages();
    ChatMessage[] addMessage(ChatMessage msg);
}
