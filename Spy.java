import java.io.*;
import java.net.*;

public class Spy {
    public static void main(String[] args) {
        int proxyPort = 10001; // The port client connects to
        String serverHost = "localhost";
        int serverPort = 10000; // Real server runs here

        try (ServerSocket proxyListener = new ServerSocket(proxyPort)) {
            System.out.println("Spy is listening for client on port " + proxyPort);
            Socket fromClient = proxyListener.accept();
            System.out.println("Client connected to spy");

            Socket toServer = new Socket(serverHost, serverPort);
            System.out.println("Spy connected to real server");

            // Forward messages: Client -> Server
            new Thread(() -> forwardWithLogging(fromClient, toServer, "Client → Server")).start();

            // Forward messages: Server -> Client
            forwardWithLogging(toServer, fromClient, "Server → Client");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void forwardWithLogging(Socket input, Socket output, String label) {
        try (
            BufferedReader reader = new BufferedReader(new InputStreamReader(input.getInputStream()));
            PrintWriter writer = new PrintWriter(output.getOutputStream(), true)
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("[" + label + "] " + line); // Spy prints this
                writer.println(line); // Forward to target
                if (line.equalsIgnoreCase("bye")) break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
