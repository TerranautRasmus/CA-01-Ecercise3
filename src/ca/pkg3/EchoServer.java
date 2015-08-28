/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */ 
package ca.pkg3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rasmus
 */
public class EchoServer implements Runnable {

    Socket s;
    PrintWriter out;
    BufferedReader in;
    String echo;
    String result;

    public static void main(String[] args) throws IOException {

        String ip = "localhost";
        int port = 4433;
        if (args.length == 2) {
            ip = args[0];
            port = Integer.parseInt(args[1]);
        }

        ServerSocket ss = new ServerSocket();
        ss.bind(new InetSocketAddress(ip, port));

        while (true) {
            EchoServer e = new EchoServer(ss.accept());
            String echo;
            Thread t1 = new Thread(e);
            t1.start();
        }
    }

    public void run() {
        while (true) {

            try {
                in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                echo = in.readLine();

                out = new PrintWriter(s.getOutputStream(), true);

                CommandChooser(echo);

                out.println(result);
            } catch (IOException ex) {
                Logger.getLogger(EchoServer.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public String CommandChooser(String echo) {
        result = echo;
        String[] splitter = echo.split("#");

        
        
        switch (splitter[0]) {
            case "UPPER":
                result = splitter[1].toUpperCase();
                break;
            case "LOWER":
                result = splitter[1].toLowerCase();
                break;
            case "REVERSE":
                result = new StringBuilder(splitter[1]).reverse().toString();
                break;
            case "TRANSLATE":
                if (splitter[1].equals("dog")) {
                    result = "hund";
                    return result;
                }
                if (splitter[1].equals("cat")) {
                    result = "kat";
                    return result;
                }
                if (splitter[1].equals("bird")) {
                    result = "fugl";
                    return result;
                }
                result = "#NOT_FOUND";
                break;
            default:
                break;
        }
        return result;
    }

    public EchoServer(Socket socket) {
        s = socket;
    }

}
