package server.multiVersion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerEcho implements Runnable{
    private Socket connectedSocekt;
    private int idThread;

    public ServerEcho(Socket socket, int numSocket) {
        this.connectedSocekt = socket;
        this.idThread = numSocket;
    }

    @Override
    public void run() {
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(connectedSocekt.getInputStream()));
            PrintWriter pw = new PrintWriter(connectedSocekt.getOutputStream(), true);

            String tmpInput;
            while ((tmpInput = bf.readLine()) != null) {
                System.out.println(idThread + "th Thread's Message : " + tmpInput);
                pw.println(tmpInput);
            }
        } catch (IOException ioException) {
            System.out.println("IOException occurred : " + ioException + "  (ServerEcho - Run Method");
        }
    }
}


