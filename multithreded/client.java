import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class client {

    public Runnable getRunnable() throws IOException {
        return new Runnable() {
            @Override
            public void run() {
                int port = 1080;
                try {
                    InetAddress address = InetAddress.getByName("localhost");
                    Socket socket = new Socket(address, port);
                    PrintWriter toSocket = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader fromSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    toSocket.println("Hello from Client" + socket.getLocalSocketAddress());
                    String line = fromSocket.readLine();
                    System.out.println("Message from Server: " + line);
                    toSocket.close();
                    fromSocket.close();
                    socket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
    }

    public static void main(String[] args) {
        client client = new client();
        for (int i = 0; i < 100; i++) {
            try {
                Thread thread = new Thread(client.getRunnable());
                thread.start();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

} 