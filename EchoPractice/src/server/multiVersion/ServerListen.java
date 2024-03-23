package server.multiVersion;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListen {
    private static int count = 0;
    public static void main(String[] args) {
        System.out.println("Echo Server on!!!");
        try {
            ServerSocket serverSocket = new ServerSocket(54321);

            while (true) {
                Socket acceptedSocket = serverSocket.accept();
                System.out.println(++count + "th Thread's connected!!");
                new Thread(new ServerEcho(acceptedSocket, count)).start();
            }
        } catch (IOException ioException) {
            System.out.println("IOException occurred : " + ioException);
        }
    }
}
