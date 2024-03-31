import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ClientGUI extends JFrame {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 5000;

    private int clientId;
    private BufferedReader reader;
    private PrintWriter writer;
    private JTextArea chatArea;
    private JTextField messageField;

    public ClientGUI() {
        setTitle("Chat Client");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        add(scrollPane);

        messageField = new JTextField();
        messageField.addActionListener(e -> sendMessage());
        add(messageField, "South");

        connectToServer();
    }

    private void connectToServer() {
        try {
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
            String idMessage = reader.readLine();
            clientId = Integer.parseInt(idMessage.substring(3));
            chatArea.append("ID: " + clientId + "\n");
            Thread receiveThread = new Thread(this::receiveMessages);
            receiveThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        String message = messageField.getText().trim();
        if (!message.isEmpty()) {
            writer.println(clientId + ":" + message);
            chatArea.append(clientId + ":" + message);
            messageField.setText("");
        }
    }

    private void receiveMessages() {
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                chatArea.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ClientGUI clientGUI = new ClientGUI();
            clientGUI.setVisible(true);
        });
    }
}
