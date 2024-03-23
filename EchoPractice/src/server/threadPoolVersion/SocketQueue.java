package server.threadPoolVersion;

import java.io.InputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

public class SocketQueue {
    private static Queue<Socket> sockets = new LinkedList<>();
    static synchronized void addSocket(Socket socket) {
        sockets.add(socket);
    }
    static synchronized Optional<Socket> hasMoreTask() {
        if (!sockets.isEmpty())
            return Optional.of(sockets.poll());
        else
            return Optional.empty();
    }
    static boolean isEmpty() {
        return sockets.isEmpty();
    }

}
