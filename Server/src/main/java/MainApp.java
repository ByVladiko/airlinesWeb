import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainApp {

    private static final int PORT = 5000;
    private static ServerSocket server;
    private static DataOutputStream ois;
    private static DataInputStream in;

    public static void main(String[] args) {
        try {
            server = new ServerSocket(PORT);
            System.out.println("Connection...");
            Socket client = server.accept();
            System.out.println("Welcome " + client.getInetAddress());
            ois = new DataOutputStream(client.getOutputStream());
            in = new DataInputStream(client.getInputStream());
            while(!client.isClosed()) {
                System.out.println("Server reading from channel");

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ois.close();
                in.close();
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
