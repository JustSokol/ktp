package ru.sokolov.as.ktp.standalone.adapter;

import ru.sokolov.as.ktp.word.WeightedWord;
import ru.sokolov.as.ktp.word.WeightedWordUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ASokolov on 27.12.2015.
 */
public class FormatAdapter {

    private List<WeightedWord> weightedWordList;

    private List<String> prefixList;

    public void parseStream(InputStream inputStream) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        weightedWordList = WeightedWordUtils.parseVocabularyFromReader(bufferedReader);

        int prefixListSize = Integer.parseInt(bufferedReader.readLine());
        prefixList = new ArrayList<>(prefixListSize);
        for( int i = 0 ; i < prefixListSize; i++){
            String line = bufferedReader.readLine();
            prefixList.add(line);
        }
    }


    public List<WeightedWord> getWeightedWordList() {
        return weightedWordList;
    }

    public void setWeightedWordList(List<WeightedWord> weightedWordList) {
        this.weightedWordList = weightedWordList;
    }

    public List<String> getPrefixList() {
        return prefixList;
    }

    public void setPrefixList(List<String> prefixList) {
        this.prefixList = prefixList;
    }
}
