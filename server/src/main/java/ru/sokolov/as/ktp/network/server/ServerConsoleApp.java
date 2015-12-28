package ru.sokolov.as.ktp.network.server;

import ru.sokolov.as.ktp.trie.Trie;
import ru.sokolov.as.ktp.word.WeightedWord;
import ru.sokolov.as.ktp.word.WeightedWordUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by ASokolov on 28.12.2015.
 */
public class ServerConsoleApp {

    public static void main(String[] args) throws IOException {

        String vocabularyFilePathString = args[0];
        int port = Integer.parseInt(args[1]);


        List<WeightedWord> weightedWords;
        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(vocabularyFilePathString))) {
            weightedWords = WeightedWordUtils.parseVocabularyFromReader(bufferedReader);
        }
        Trie trie = new Trie();
        trie.fillWithWeightedWords(weightedWords);

        ServerCore serverCore = new ServerCore(trie, port);
        serverCore.start();
    }
}
