package chat.client;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import javax.swing.*;

public class Decorator {
    private ClientGuiController chatClient;

    private MongoDatabase db;
    private MongoCollection collection;

    public ClientGuiController getChatClient() {
        return chatClient;
    }

    public void setChatClient(ClientGuiController chatClient) {
        this.chatClient = chatClient;
    }

    public static void main(String[] args) {
        Decorator decorator = new Decorator();
        if (decorator.logIn()) {
            decorator.setChatClient(new ClientGuiController());
            decorator.getChatClient().run();
        }
    }

    private void setConnection() {
        MongoClient mongoClient = MongoClients.create();
        this.db = mongoClient.getDatabase("chat");
        this.collection = db.getCollection("users");
    }

    public boolean logIn() {
        setConnection();
        while (true) {
            String login = JOptionPane.showInputDialog(
                    new JFrame(),
                    "Enter login:",
                    "Log in",
                    JOptionPane.QUESTION_MESSAGE);
            String password = JOptionPane.showInputDialog(
                    new JFrame(),
                    "Enter password:",
                    "Password",
                    JOptionPane.QUESTION_MESSAGE);
            try {
                Document document = (Document) collection
                        .find(Filters.eq("login", login))
                        .filter(Filters.eq("password", password)).first();
                if (document == null) {
                    throw new LoginException("Wrong login or password!");
                } else {
                    return true;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        new JFrame(),
                        e.getMessage(),
                        "Login",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
