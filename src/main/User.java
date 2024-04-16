package main;

import java.util.Iterator;

public class User implements IterableByUser {
    private String name;
    private ChatServer chatServer;
    private ChatHistory history;
    private Message lastMessage;

    public User(String name) {
        this.name = name;
        this.history = new ChatHistory();
    }

    public String getName() {
        return name;
    }

    public ChatServer getChatServer() {
        return chatServer;
    }

    public ChatHistory getHistory() {
        return history;
    }

    public void readChatHistory() {
        System.out.printf("%s's Chat History\n", name);
        System.out.println("-----------------------------");
        history.readChatHistory();
        System.out.println();
    }

    public Message getLastMessage() {
        return lastMessage;
    }

    public void setChatServer(ChatServer chatServer) {
        this.chatServer = chatServer;
    }

    public void sendMessage(String content, User... recipients) {
        Message msg = new Message(content, this, recipients);
        if (chatServer.sendMessage(msg)) {
            lastMessage = msg;
            history.addMessage(msg);
        }
    }

    public void receiveMessage(Message msg) {
        history.addMessage(msg);
    }

    public void blockUser(User user) {
        chatServer.blockUser(this, user);
    }

    public void unblockUser(User user) {
        chatServer.unblockUser(this, user);
    }

    public void undoLastMessage() {
        chatServer.undoMessage(lastMessage);
        history.undoLastMessage();
        lastMessage = history.getLastMessage();
    }

    @Override
    public Iterator<Message> iterator(User userToSearchWith) {
        return history.iterator(userToSearchWith);
    }
}
