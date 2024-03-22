package multiVersion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public static void main(String[] args) {
        try {
            ServerSocket listeningSocket = new ServerSocket(54321);
            Socket connectedSocket = listeningSocket.accept();

            System.out.println("Socket Connected!");

            BufferedReader bf = new BufferedReader(new InputStreamReader(connectedSocket.getInputStream()));
            PrintWriter pw = new PrintWriter(connectedSocket.getOutputStream(), true);

            String inputString;
            while ((inputString = bf.readLine()) != null) {
                pw.println(inputString);
                System.out.println("input = " + inputString);
            }

            System.out.println("Sockect Disconnected!");
            bf.close();
            pw.close();
            connectedSocket.close();
            listeningSocket.close();
        } catch (IOException ioException) {
            System.out.println("IOException orccured : " + ioException);
        }
    }


}
