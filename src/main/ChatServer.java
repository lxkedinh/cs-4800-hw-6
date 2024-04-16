package main;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class ChatServer {
    private HashSet<User> registeredUsers = new HashSet<>();
    private HashMap<User, HashSet<User>> blockedUsers = new HashMap<>();

    public ChatServer() {}

    public HashSet<User> getRegisteredUsers() {
        return registeredUsers;
    }

    public HashMap<User, HashSet<User>> getBlockedUsers() {
        return blockedUsers;
    }

    public void registerUser(User user) {
        user.setChatServer(this);
        registeredUsers.add(user);
        blockedUsers.put(user, new HashSet<>());
    }

    public void unregisterUser(User user) {
        user.setChatServer(null);
        registeredUsers.remove(user);
        blockedUsers.remove(user);
    }

    public boolean sendMessage(Message msg) {
        User sender = msg.getSender();
        List<User> recipients = msg.getRecipients();

        if (!registeredUsers.contains(sender)) {
            System.out.printf("User '%s' is not registered. Please register first!\n", sender.getName());
            return false;
        }

        if (isUserBlockedByRecipients(msg)) {
            return false;
        }

        for (User recipient : recipients) {
            recipient.receiveMessage(msg);
        }

        return true;
    }

    public void undoMessage(Message msg) {
        MessageMemento memento = msg.saveState();

        for (User recipient : msg.getRecipients()) {
            recipient.getHistory().removeMessage(memento);
        }
    }

    public void blockUser(User blocker, User blockee) {
        if (!registeredUsers.contains(blocker)) {
            System.out.printf("User '%s' that is trying to block is not registered. Please register first!\n", blocker.getName());
            return;
        }

        if (!registeredUsers.contains(blockee)) {
            System.out.printf("User '%s' is not registered and cannot be blocked.\n", blockee.getName());
            return;
        }

        HashSet<User> usersBlockedByBlocker = blockedUsers.get(blocker);
        usersBlockedByBlocker.add(blockee);
    }

    public void unblockUser(User blocker, User blockee) {
        if (!registeredUsers.contains(blocker)) {
            System.out.printf("User '%s' that is trying to unblock is not registered. Please register first!\n", blocker.getName());
            return;
        }

        if (!registeredUsers.contains(blockee)) {
            System.out.printf("User '%s' is not registered and cannot be unblocked.\n", blockee.getName());
            return;
        }

        HashSet<User> usersBlockedByBlocker = blockedUsers.get(blocker);
        usersBlockedByBlocker.remove(blockee);
    }

    public boolean isUserBlockedByRecipients(Message msg) {
        User sender = msg.getSender();
        List<User> recipients = msg.getRecipients();

        for (User recipient : recipients) {
            if (blockedUsers.get(recipient).contains(sender)) {
                System.out.printf("User '%s' is blocked by user '%s'. Could not send message.\n", sender.getName(), recipient.getName());
                return true;
            }
        }

        return false;
    }
}
