package main;

import java.util.Iterator;
import java.util.LinkedList;

public class SearchMessagesByUser implements Iterator<Message> {
    private LinkedList<Message> msgs;
    private int index = 0;
    private int collectionSize;
    private User userToSearchWith;

    public SearchMessagesByUser(ChatHistory history, User userToSearchWith) {
        msgs = history.getMessages();
        collectionSize = msgs.size();
        this.userToSearchWith = userToSearchWith;
    }

    @Override
    public boolean hasNext() {
        while (index < collectionSize) {
            if (msgs.get(index).getSender().equals(userToSearchWith)) {
                return true;
            }
            index++;
        }

        return false;
    }

    @Override
    public Message next() {
        if (hasNext()) {
            return msgs.get(index++);
        }

        return null;
    }
}
