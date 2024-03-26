package server.multiNioVersion;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Listening {
    private static final int PORTNUM = 54321;
    public static void main(String[] args) {
            try {
                ServerSocketChannel listeningChannel = initialize();

                while (true) {
                    SocketChannel connectedSocket = listeningChannel.accept();
                    // Make SocketChannel Non-blocking mode
                    connectedSocket.configureBlocking(false);
                    SocketStation.register(connectedSocket);
                    System.out.println("New Connection!");
                }
            } catch (IOException ioException) {
            System.out.println("IOException occurred : " + ioException);
        }
    }

    private static ServerSocketChannel initialize() throws IOException {
        // Create Listening Socket
        ServerSocketChannel listeningChannel = ServerSocketChannel.open();
        listeningChannel.socket().bind(new InetSocketAddress(PORTNUM));
        // Start Echo Thread
        new Thread(new EchoService()).start();
        return listeningChannel;
    }
}
