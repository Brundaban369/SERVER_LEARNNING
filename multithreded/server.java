import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class server {
     public Consumer<Socket> getConsumer() {
        return (acceptedsocket) -> {
            try (PrintWriter toSocket = new PrintWriter(acceptedsocket.getOutputStream(), true)) {
                toSocket.println("Hello World from server " + acceptedsocket.getInetAddress());
                toSocket.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        };

    }
     public static void main(String[] args) {
        int port = 1080;
        server server = new server();
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(10000);
            System.out.println("Server is listening on port " + port);
            while (true) {
                Socket acceptedsocket = serverSocket.accept();
                Thread thread = new Thread(() -> server.getConsumer().accept(acceptedsocket));
                thread.start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
