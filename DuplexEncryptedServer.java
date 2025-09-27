import java.util.Base64;
import java.io.*;
import java.net.*;
import javax.crypto.SecretKey;

public class DuplexEncryptedServer {
    public static void main(String[] args) {
        int port = 10000;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started. Waiting for client...");

            Socket socket = serverSocket.accept();
            System.out.println("Client connected!");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

            // Shared secret key (must match client's key)
            String base64Key = "YWJjZGVmZ2hpamtsbW5vcA=="; // This is Base64 for "mindsecretkey123"
            byte[] keyBytes = Base64.getDecoder().decode(base64Key);
            SecretKey key = AESUtil.getKeyFromBytes(keyBytes); // OR use a predefined byte array

            // Show key bytes (in real use, share securely)
            System.out.println("Secret Key (share with client): " + Base64.getEncoder().encodeToString(key.getEncoded()));

            // Thread to read encrypted messages from client
            new Thread(() -> {
                try {
                    String encrypted;
                    while ((encrypted = in.readLine()) != null) {
                        String decrypted = AESUtil.decrypt(encrypted, key);
                        System.out.println("Client: " + decrypted);
                        if (decrypted.equalsIgnoreCase("bye")) break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

            // Sending encrypted messages to client
            String input;
            while ((input = console.readLine()) != null) {
                String encrypted = AESUtil.encrypt(input, key);
                out.println(encrypted);
                System.out.println(encrypted);
                if (input.equalsIgnoreCase("bye")) break;
            }

            socket.close();
            System.out.println("Server disconnected.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

