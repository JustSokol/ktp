package ru.sokolov.as.ktp.standalone;

import java.io.*;

/**
 * Created by ASokolov on 27.12.2015.
 */
public class ConsoleApplication {
    public static void main(String[] args) throws IOException {

        StreamStandaloneApp streamStandaloneApp = new StreamStandaloneApp();
        String result = streamStandaloneApp.process(System.in);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(System.out);
        outputStreamWriter.write(result);
        outputStreamWriter.flush();
    }
}
