import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final int PORT = 5000;
    private static List<ServerThread> clients = new ArrayList<>();
    private static Map<ServerThread,Integer> clientList = new HashMap<>();
    private static int nextClientId = 0;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started. Listening on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);

                ServerThread serverThread = new ServerThread(clientSocket, nextClientId++);
                clients.add(serverThread);
                clientList.put(serverThread,nextClientId-1);
                System.out.println(clientList);
                new Thread(serverThread).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void broadcast(String message) {
        for (ServerThread client : clients) {
            client.sendMessage(message);
        }
    }
}
