package com.chatApp.wehelpsu.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.chatApp.wehelpsu.shared.ChatMessage;


public class ChatContent implements Content {

    private VerticalPanel mainPanel = new VerticalPanel();
    private HorizontalPanel sendPanel = new HorizontalPanel();
    private TextBox messageTextBox = new TextBox();
    private Button sendButton = new Button();
    private FlexTable messagesFlexTable = new FlexTable();

    private ChatMessagesServiceAsync chatMessagesService = GWT.create(ChatMessagesService.class);

    @Override
    public Widget getContent() {
        sendButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                submitMessage();
            }
        });

        messageTextBox.addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(final KeyPressEvent event) {
                if (event.getCharCode() == KeyCodes.KEY_ENTER) {
                    submitMessage();
                }
            }
        });

        sendButton.setText("Send");

        sendPanel.add(messageTextBox);
        sendPanel.add(sendButton);

        mainPanel.add(new Label("Your username: " + Cookies.getCookie(LoginContent.COOKIE_NAME)));
        mainPanel.add(sendPanel);
        mainPanel.add(messagesFlexTable);

        messageTextBox.setFocus(true);

        final Timer t = new Timer() {
            @Override
            public void run() {
                refreshMessages();
            }
        };

        t.scheduleRepeating(1000);

        return mainPanel;
    }

    private void submitMessage() {
    	String msg= messageTextBox.getText().trim();
    	if(msg.equals("")||msg==""){
    		messageTextBox.setText("");
    		return;
    	}
        final ChatMessage m = new ChatMessage(Cookies.getCookie(LoginContent.COOKIE_NAME),msg);

        chatMessagesService.addMessage(m, new AsyncCallback<ChatMessage[]>() {
            @Override
            public void onFailure(final Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onSuccess(final ChatMessage[] chatMessages) {
                messageTextBox.setText("");
                updateTable(chatMessages);
            }
        });
    }

    private void refreshMessages() {
        chatMessagesService.getMessages(new AsyncCallback<ChatMessage[]>() {
            @Override
            public void onFailure(final Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onSuccess(final ChatMessage[] chatMessages) {
                updateTable(chatMessages);
            }
        });
    }

    private void updateTable(final ChatMessage[] messages) {
        final int row = messagesFlexTable.getRowCount();

        if (messages.length > row) {
            for (int i = row; i < messages.length; i++) {
                final ChatMessage m = messages[i];
                messagesFlexTable.setText(i, 0, m.getUser());
                messagesFlexTable.setText(i, 1, m.getMessage());
            }
        }
    }
}
