import java.io.*;
import java.net.*;
import javax.crypto.SecretKey;
import java.util.Base64;

public class DuplexEncryptedClient {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 10001;

        try (Socket socket = new Socket(host, port)) {
            System.out.println("Connected to server.");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

            // Paste the server's secret key string here (from console output)
            String base64Key = "YWJjZGVmZ2hpamtsbW5vcA=="; // example: "xsZfjQ3HuMwywWP76+FxzQQ=="
            byte[] keyBytes = Base64.getDecoder().decode(base64Key);
            SecretKey key = AESUtil.getKeyFromBytes(keyBytes);

            // Thread to read encrypted messages from server
            new Thread(() -> {
                String encrypted;
                try {
                    while ((encrypted = in.readLine()) != null) {
                        String decrypted = AESUtil.decrypt(encrypted, key);
                        System.out.println("Server: " + decrypted);
                        if (decrypted.equalsIgnoreCase("bye")) break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

            // Sending encrypted messages to server
            String input;
            while ((input = console.readLine()) != null) {
                String encrypted = AESUtil.encrypt(input, key);
                out.println(encrypted);
                if (input.equalsIgnoreCase("bye")) break;
            }

            socket.close();
            System.out.println("Client disconnected.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

