package test;

import main.ChatServer;
import main.Message;
import main.SearchMessagesByUser;
import main.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private ChatServer chatServer;

    private User user;
    private User user2;
    private User user3;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
        chatServer = new ChatServer();
        user = new User("Luke");
        user2 = new User("John");
        user3 = new User("Sean");

        chatServer.registerUser(user);
        chatServer.registerUser(user2);
        chatServer.registerUser(user3);
    }

    @Test
    void shouldGetName() {
        assertEquals("Luke", user.getName());
    }

    @Test
    void shouldSendMessageToOneUser() {
        assertDoesNotThrow(() -> user.sendMessage("Hi there John!", user2));

        assertEquals(1, user2.getHistory().getMessages().size());
    }

    @Test
    void shouldSendMessageToMultipleUsers() {
        assertDoesNotThrow(() -> user.sendMessage("Hey guys!", user2, user3));

        user2.readChatHistory();
        user3.readChatHistory();
        assertEquals(1, user2.getHistory().getMessages().size());
        assertEquals(1, user3.getHistory().getMessages().size());
    }

    @Test
    void shouldBlockUser() {
        user2.blockUser(user);

        user.sendMessage("Hi there!", user2);

        assertEquals(0, user2.getHistory().getMessages().size());
    }

    @Test
    void shouldUnblockUser() {
        user2.blockUser(user);
        user2.unblockUser(user);

        user.sendMessage("Hi there!", user2);
        assertEquals(1, user2.getHistory().getMessages().size());
    }

    @Test
    void shouldUndoLastMessage() {
        user.sendMessage("Hi there!", user2);
        assertDoesNotThrow(() -> user.undoLastMessage());
        assertEquals(0, user.getHistory().getMessages().size());
        assertEquals(0, user2.getHistory().getMessages().size());
    }

    @Test
    void shouldIterateMessagesByUserFilter() {
        user.sendMessage("Hi there!", user2);
        user.sendMessage("How have classes been for you?", user2);
        user2.sendMessage("msg should not be in filter", user3);
        user.sendMessage("My classes have been really stressful lately.", user2);

        int filteredMessages = 0;

        Iterator<Message> iterator = user2.iterator(user);

        while (iterator.hasNext()) {
            iterator.next();
            filteredMessages++;
        }

        assertEquals(3, filteredMessages);
    }
}