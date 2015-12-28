package ru.sokolov.as.ktp.performance;

import org.junit.Ignore;
import org.junit.Test;
import ru.sokolov.as.ktp.standalone.StreamStandaloneApp;
import ru.sokolov.as.ktp.standalone.adapter.FormatAdapter;
import ru.sokolov.as.ktp.trie.Trie;
import ru.sokolov.as.ktp.word.WeightedWord;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by ASokolov on 27.12.2015.
 */
public class PerformanceTest {

    private static final String testFileName = "test.in";

    private long firstClockTime;

    @Test
    @Ignore
    public void experimentalTest() throws IOException, URISyntaxException {

        try (InputStream inputStream = openFileFromClasspath(testFileName)) {
            FormatAdapter formatAdapter = new FormatAdapter();
            startClock();
            formatAdapter.parseStream(inputStream);
            printClockTime("Parse stream into WeightedWords and prefixes");
            List<WeightedWord> weightedWordList = formatAdapter.getWeightedWordList();
        }
    }

    @Test
    @Ignore
    public void appTest() throws IOException, URISyntaxException {

        try (InputStream inputStream = openFileFromClasspath(testFileName)) {
            startClock();
            String result = new StreamStandaloneApp().process(inputStream);
            printClockTime("StreamStandaloneApp - process");
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(System.out);
            outputStreamWriter.write(result);
            printClockTime("OutputStreamWriter - end writing");
        }
    }

    @Test
    @Ignore
    public void nativeAlgorithmTest() throws IOException, URISyntaxException {

        Path path = Paths.get(ClassLoader.getSystemResource(testFileName).toURI());
        List<String> strings = Files.readAllLines(path);

        startClock();

        int weightedWordsNumber = Integer.parseInt(strings.get(0));
        List<String> weightedWordsStringList = strings.subList(1, weightedWordsNumber);
        Trie trie = new Trie();
        for (String weightedWordString : weightedWordsStringList) {
            trie.addWord(FormatAdapter.parseWeightedWordFromLine(weightedWordString));
        }
        printClockTime("Trie filling");


        startClock();
        List<String> prefixList = strings.subList(weightedWordsNumber + 2, strings.size());
        for (String prefix : prefixList) {
//            System.out.println(String.format("Prefix %s:", prefix));
            List<WeightedWord> weightedWordList = trie.searchLimitedWordsByPrefix(prefix);
//            if (weightedWordList != null) {
//                for (WeightedWord weightedWord : weightedWordList) {
//                    System.out.println(String.format("%s %d", weightedWord.getWord(), weightedWord.getWeight()));
//                }
//                System.out.println();
//            }
        }
        printClockTime("Trie prefix searches");
    }

    private void startClock() {
        firstClockTime = System.currentTimeMillis();
    }

    private void printClockTime(String clockEventName) {
        System.out.println(String.format("Clock for: %s .", clockEventName));
        printClockTime();
    }

    private void printClockTime() {
        System.out.println("Time Millis dif:" + (System.currentTimeMillis() - firstClockTime));
    }

    private InputStream openFileFromClasspath (String fileName) throws URISyntaxException, FileNotFoundException {
        Path path = Paths.get(ClassLoader.getSystemResource(fileName).toURI());
        return new FileInputStream(path.toFile());
    }


}
