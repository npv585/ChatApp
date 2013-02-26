package com.chatApp.wehelpsu.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.chatApp.wehelpsu.shared.ChatMessage;


public interface ChatMessagesServiceAsync {
    void getMessages(AsyncCallback<ChatMessage[]> callback);
    void addMessage(ChatMessage msg, AsyncCallback<ChatMessage[]> callback);
}
