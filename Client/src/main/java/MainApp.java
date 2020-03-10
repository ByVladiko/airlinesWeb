import java.io.*;
import java.net.Socket;

public class MainApp {

    private static Socket socket;
    private static BufferedReader br;
    private static DataOutputStream oos;
    private static DataInputStream ois;

    public static void main(String[] args) {

        try {
            socket = new Socket("localhost", 5000);
            br = new BufferedReader(new InputStreamReader(System.in));
            oos = new DataOutputStream(socket.getOutputStream());
            ois = new DataInputStream(socket.getInputStream());

            System.out.println("Client connected to socket.");

            while (!socket.isOutputShutdown()) {

            }
            System.out.println("Connection was closed");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ois.close();
                socket.close();
                br.close();
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
