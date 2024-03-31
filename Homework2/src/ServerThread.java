import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ServerThread implements Runnable {
    private Socket clientSocket;
    private BufferedReader reader;
    private PrintWriter writer;
    private int clientId;

    public ServerThread(Socket clientSocket, int clientId) {
        try {
            this.clientSocket = clientSocket;
            this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.writer = new PrintWriter(clientSocket.getOutputStream(), true);
            this.clientId = clientId;
            initialization("ID:"+ String.valueOf(clientId));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Received message from client:" + clientId + " msg-> " + line);
                String[] tokens = line.split(":");
                System.out.println(tokens.length);
                if(tokens.length == 2) {
                    System.out.println(tokens[0] + " " + tokens[1] + " ");
                    Server.broadcast(line);
                }
                else if (tokens.length == 3){
                    System.out.println(tokens[0] + " " + tokens[1] + " " + tokens[2]);
                    int receiverId = Integer.parseInt(tokens[1]);
                    System.out.println(receiverId);
                    Server.whisper(line,receiverId);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void initialization(String message){
        writer.println(message);
    }

    public void sendMessage(String message) {
        writer.println(message);
//        writer.println("TESTING TESTING TESTING \n");
    }
    public int getClientId() {
        return clientId;
    }
}
