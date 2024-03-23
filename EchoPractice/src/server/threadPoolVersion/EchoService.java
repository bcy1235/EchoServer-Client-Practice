package server.threadPoolVersion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Optional;

public class EchoService implements Runnable{
    private int idThread;
    public EchoService(int numThread) {
        this.idThread = numThread;
    }
    @Override
    public void run() {
        while (true) {
            Optional<Socket> socket = SocketQueue.hasMoreTask();
            if (socket.isEmpty()) {
                synchronized (Thread.currentThread()) {
                    try {
                        Thread.currentThread().wait();

                    } catch (InterruptedException e) {

                    } finally {
                        continue;
                    }
                }
            }

            try {
                Socket connectedSocket = socket.get();
                System.out.println(idThread + " : Thread Working!");
                BufferedReader bf = new BufferedReader(new InputStreamReader(connectedSocket.getInputStream()));
                PrintWriter pw = new PrintWriter(connectedSocket.getOutputStream(), true);

                String tmpInput = bf.readLine();
                if (tmpInput == null) {
                    connectedSocket.close();
                } else {
                    pw.println(tmpInput);
                    SocketQueue.addSocket(connectedSocket);
                }

            } catch (IOException ioException) {
                System.out.println("IOException occurred : " + ioException + "  (EchoService - Run Method)");
            }
        }
    }
}


