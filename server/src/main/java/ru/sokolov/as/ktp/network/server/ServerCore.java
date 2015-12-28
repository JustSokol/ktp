package ru.sokolov.as.ktp.network.server;

import ru.sokolov.as.ktp.trie.Trie;
import ru.sokolov.as.ktp.word.WeightedWord;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Created by ASokolov on 28.12.2015.
 */
public class ServerCore {

    private final Trie trie;
    private final int port;
    private final Charset protocolCharset;

    public ServerCore(Trie trie, int port) {
        this(trie, port, StandardCharsets.US_ASCII);
    }

    public ServerCore(Trie trie, int port, Charset charset) {
        this.trie = trie;
        this.port = port;
        protocolCharset = charset;
    }

    public void start() throws IOException {

        ServerSocket serverSocket = new ServerSocket(port);

        while (true) {
            Socket socket = serverSocket.accept();
            new Thread(() -> {
                try {
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), protocolCharset));
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), protocolCharset));
                    while (socket.isConnected()) {
                        String prefix = bufferedReader.readLine();
                        List<WeightedWord> weightedWords = trie.searchLimitedWordsByPrefix(prefix);
                        for (WeightedWord weightedWord : weightedWords) {
                            bufferedWriter.write(weightedWord.getWord());
                        }
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                    }
                } catch (IOException e) {
                    new RuntimeException(e);
                } finally {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public int getPort() {
        return port;
    }


    public Trie getTrie() {
        return trie;
    }

    public Charset getProtocolCharset() {
        return protocolCharset;
    }

}
