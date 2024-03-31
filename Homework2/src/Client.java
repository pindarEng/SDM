import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 5000;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);

            int[] clientId = {-1};
            String idMessage = reader.readLine();
            if (idMessage.startsWith("ID:")) {
                clientId[0] = Integer.parseInt(idMessage.substring(3));
                System.out.println("Connected to server. Your ID is: " + clientId[0]);
            } else {
                System.out.println("Invalid ID message received.");
                System.exit(1);
            }

            Thread messageReceiver = new Thread(() -> {
                try {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (isWhisperForClient(line,clientId[0])){
                            System.out.println(line);
                        }else {
                            System.out.println(line);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            messageReceiver.start();

            while (true) {
                System.out.print("");
                String input = scanner.nextLine();
                writer.println(clientId[0] + ":" + input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static boolean isWhisperForClient(String message, int clientId){
        String[] parts = message.split(":");
        if(parts.length == 3){
            int receiverId = Integer.parseInt(parts[1]);
            System.out.println(receiverId);
            return receiverId == clientId;
        }
        return false;
    }

}
