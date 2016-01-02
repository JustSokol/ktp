package ru.sokolov.as.ktp.performance;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import ru.sokolov.as.ktp.standalone.StreamStandaloneApp;
import ru.sokolov.as.ktp.standalone.adapter.FormatAdapter;
import ru.sokolov.as.ktp.trie.Trie;
import ru.sokolov.as.ktp.word.WeightedWord;
import ru.sokolov.as.ktp.word.WeightedWordUtils;

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

    /**
     * Полное тестирование работы приложения с учетом времени вывода на консоль.
     * @throws IOException
     * @throws URISyntaxException
     */
    @Test
    public void appTest() throws IOException, URISyntaxException {

        try (InputStream inputStream = openFileFromClasspath(testFileName)) {
            startClock();
            String result = new StreamStandaloneApp().process(inputStream);
            printClockTime("StreamStandaloneApp - process");
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(System.out);
            outputStreamWriter.write(result);
            outputStreamWriter.flush();
            long totalWorkTime = printClockTime("Total app work time");
            Assert.assertTrue("Скорость работы приложения на тестовом файле превысила 10с",totalWorkTime < 10_000L);
        }
    }

    /**
     * Тестирование работы классов Trie, TrieNode, WeightedWord - т.е. скорости работы самого алгоритма и структуры данных без работы потоков ввода вывода.
     * @throws IOException
     * @throws URISyntaxException
     */
    @Test
    public void nativeAlgorithmTest() throws IOException, URISyntaxException {

        Path path = Paths.get(ClassLoader.getSystemResource(testFileName).toURI());
        List<String> strings = Files.readAllLines(path);

        int weightedWordsNumber = Integer.parseInt(strings.get(0));
        List<String> weightedWordsStringList = strings.subList(1, weightedWordsNumber);
        Trie trie = new Trie();

        long startClock = startClock();
        for (String weightedWordString : weightedWordsStringList) {
            trie.addWord(WeightedWordUtils.parseWeightedWordFromLine(weightedWordString));
        }
        printClockTime("Trie filling");

        List<String> prefixList = strings.subList(weightedWordsNumber + 2, strings.size());
        startClock();
        for (String prefix : prefixList) {
            List<WeightedWord> weightedWordList = trie.searchLimitedWordsByPrefix(prefix);
        }
        long totalTime = System.currentTimeMillis()-startClock;

        printClockTime("Trie prefix searches");

        System.out.println("Total time: "+totalTime);
        Assert.assertTrue("Скорость работы алгоритма на тестовом файле превысила 10с", totalTime < 10_000L);
    }

    @Test
    @Ignore
    public void experimentalTest() throws IOException, URISyntaxException {

        try (InputStream inputStream = openFileFromClasspath(testFileName)) {
            FormatAdapter formatAdapter = new FormatAdapter();
            startClock();
            formatAdapter.parseStream(inputStream);
            printClockTime("Parse stream into WeightedWords and prefixes");
        }
    }


    private long startClock() {
        return firstClockTime = System.currentTimeMillis();
    }

    private long printClockTime(String clockEventName) {
        System.out.println(String.format("Clock for: %s .", clockEventName));
        return printClockTime();
    }

    private long printClockTime() {
        long difference = System.currentTimeMillis() - firstClockTime;
        System.out.println("Time Millis dif:" + difference);
        return difference;
    }

    private InputStream openFileFromClasspath (String fileName) throws URISyntaxException, FileNotFoundException {
        Path path = Paths.get(ClassLoader.getSystemResource(fileName).toURI());
        return new FileInputStream(path.toFile());
    }
}
