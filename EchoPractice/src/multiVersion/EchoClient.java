package multiVersion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class EchoClient {
    public static void main(String[] args) {
        try {
            Socket clientSocket = new Socket("127.0.0.1", 54321);
            BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter pw = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader socketInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String input;
            while (!(input = bf.readLine()).equals("EXIT")) {
                pw.println(input);
                System.out.println("ECHO : " + socketInput.readLine());
            }

            socketInput.close();
            pw.close();
            clientSocket.close();
        } catch (UnknownHostException unknownHostException) {
            System.out.println("Cannot find host with given ip : " + unknownHostException );;
        } catch (IOException ioException) {
            System.out.println("IOException occurred : " + ioException);
        }
    }
}
