import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
private static ServerSocket server;
    private static final String IP_ADRESS="localhost";
    private static final int PORT=8189;
    private static Socket socket;
    private static Scanner sc;
    private static DataInputStream in;
    private static DataOutputStream out;


    public static void main(String[] args) {
        try {
            server = new ServerSocket(8189);
            System.out.println("Server started");
            socket = server.accept();
            System.out.println("Client connected");
            in = new DataInputStream(socket.getInputStream());
            sc = new Scanner(System.in);
            out = new DataOutputStream(socket.getOutputStream());
            Thread  read= new Thread(() -> {
                while (true) {
                    try {
                        out.writeUTF(sc.nextLine());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            read.setDaemon(true);
            read.start();
            while (true) {

                String  msg = in.readUTF();
                if (msg.equals("/end")) {
                    System.out.println("Client disconneccted");
                    break;
                }
                System.out.println("Client: " + msg);


            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    socket.close();
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }



    }
}
