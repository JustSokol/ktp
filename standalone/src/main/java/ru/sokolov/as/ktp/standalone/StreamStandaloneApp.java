package ru.sokolov.as.ktp.standalone;

import ru.sokolov.as.ktp.standalone.adapter.FormatAdapter;
import ru.sokolov.as.ktp.trie.Trie;
import ru.sokolov.as.ktp.word.WeightedWord;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by ASokolov on 27.12.2015.
 */
public class StreamStandaloneApp {

    public String process(InputStream inputStream) throws IOException {

        Trie trie = new Trie();
        FormatAdapter formatAdapter = new FormatAdapter();
        formatAdapter.parseStream(inputStream);
        List<WeightedWord> weightedWordList = formatAdapter.getWeightedWordList();
        trie.fillWithWeightedWords(weightedWordList);

        StringBuilder resultStringBuilder = new StringBuilder();
        List<String> prefixList = formatAdapter.getPrefixList();

        for (String prefix : prefixList) {
            List<WeightedWord> weightedWordsByPrefix = trie.searchLimitedWordsByPrefix(prefix);
            if (weightedWordsByPrefix != null) {
                for (WeightedWord weightedWord : weightedWordsByPrefix) {
                    resultStringBuilder.append(weightedWord.getWord() + "\n");
                }
                resultStringBuilder.append("\n");
            }
        }
        return resultStringBuilder.toString();
    }

}
