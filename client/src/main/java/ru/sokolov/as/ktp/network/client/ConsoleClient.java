package ru.sokolov.as.ktp.network.client;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Created by ASokolov on 28.12.2015.
 */
public class ConsoleClient {

    public static void main(String[] args) throws IOException {

        String hostName = args[0];
        int port = Integer.parseInt(args[1]);

        try (Socket clientSocket = new Socket(hostName, port)) {
            OutputStream socketOutputStream = clientSocket.getOutputStream();
            BufferedReader consoleInputReader = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String inputLine = consoleInputReader.readLine();
                String request = "get " + inputLine;
                socketOutputStream.write(request.getBytes(StandardCharsets.US_ASCII));
                socketOutputStream.flush();
            }
        }
    }
}
