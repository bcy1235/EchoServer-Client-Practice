package server.threadPoolVersion;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Listening {
    private static int count = 0;
    public static void main(String[] args) {
            try {
                ServerSocket serverSocket = new ServerSocket(54321);
                initialize();

                while (true) {
                    Socket acceptedSocket = serverSocket.accept();
                    SocketQueue.addSocket(acceptedSocket);
                }
            } catch (IOException ioException) {
            System.out.println("IOException occurred : " + ioException);
        }
    }

    private static void initialize() {
        ThreadPool.initialize();
        new Thread(new Waiting()).start();
        System.out.println("Echo Server on!!!");
    }
}
