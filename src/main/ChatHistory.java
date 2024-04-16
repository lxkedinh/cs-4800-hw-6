package main;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

public class ChatHistory implements IterableByUser {
    private LinkedList<Message> messages = new LinkedList<>();

    public ChatHistory() {}

    public LinkedList<Message> getMessages() {
        return messages;
    }

    public void addMessage(Message msg) {
        messages.add(msg);
    }

    public Message getLastMessage() {
        if (!messages.isEmpty()) {
            return messages.peek();
        }

        return null;
    }

    public Message undoLastMessage() {
        if (!messages.isEmpty()) {
            return messages.pop();
        }

        return null;
    }

    public void removeMessage(MessageMemento msg) {
        messages.removeIf(m -> m.toString().equals(msg.getContent()));
    }

    public void readChatHistory() {
        for (Message msg : messages) {
            System.out.println(msg);
        }
    }

    @Override
    public Iterator<Message> iterator(User userToSearchWith) {
        return new SearchMessagesByUser(this, userToSearchWith);
    }
}
