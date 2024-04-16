package main;

import java.util.Iterator;

public class Driver {


    public static void main(String[] args) {
        User luke = new User("Luke");
        User john = new User("John");
        User sean = new User("Sean");

        ChatServer server = new ChatServer();
        server.registerUser(luke);
        server.registerUser(john);
        server.registerUser(sean);

        System.out.println("Users sending messages to one or more users through the chat server");
        luke.sendMessage("Hi John!", john);
        sean.sendMessage("Hey guys!", luke, john);

        System.out.println("Users can receive messages from other users and can view chat history of specific users");
        luke.readChatHistory();
        john.readChatHistory();

        System.out.println("Users can undo the last message they sent");
        sean.readChatHistory();
        sean.undoLastMessage();
        sean.readChatHistory();

        System.out.println("Users can block messages from specific users");
        john.blockUser(luke);
        luke.sendMessage("Hey John, want to hang out on Tuesday?", john);

        john.unblockUser(luke);
        System.out.println();

        // Search By User Iterator
        luke.sendMessage("How are your classes?", john);
        luke.sendMessage("This semester's really stressful for me.", john);
        sean.sendMessage("Hey John, want to hang out on Wednesday?", john);

        Iterator<Message> iterator = john.iterator(luke);
        System.out.println("John's chat history filtered by sender 'Luke' (Search By User Iterator)");
        System.out.println("----------------------");
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
