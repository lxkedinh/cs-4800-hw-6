package main;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Message {
    private User sender;
    private List<User> recipients = new ArrayList<>();
    private LocalDateTime timestamp = LocalDateTime.now();
    private String content;

    public Message(String content, User sender, User... recipients) {
        this.sender = sender;
        this.content = content;
        Collections.addAll(this.recipients, recipients);
    }

    public User getSender() {
        return sender;
    }

    public List<User> getRecipients() {
        return recipients;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public MessageMemento saveState() {
        return new MessageMemento(this.toString(), timestamp);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("[")
                .append(timestamp.getHour())
                .append(":")
                .append(timestamp.getMinute())
                .append(":")
                .append(timestamp.getSecond())
                .append("]");
        s.append(" ")
                .append(sender.getName())
                .append(" -> ");

        for (User recipient : recipients) {
            s.append(recipient.getName())
                    .append(", ");
        }

        s.append(" \"")
                .append(content)
                .append("\"");

        return s.toString();
    }
}
