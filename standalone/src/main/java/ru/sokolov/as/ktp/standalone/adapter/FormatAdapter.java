package ru.sokolov.as.ktp.standalone.adapter;

import ru.sokolov.as.ktp.word.WeightedWord;

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

        int vocabularySize = Integer.parseInt(bufferedReader.readLine());
        weightedWordList = new ArrayList<>(vocabularySize);
        for (int i = 0; i < vocabularySize; i++) {
            String line = bufferedReader.readLine();
            weightedWordList.add(parseWeightedWordFromLine(line));
        }
        int prefixListSize = Integer.parseInt(bufferedReader.readLine());
        prefixList = new ArrayList<>(prefixListSize);
        for( int i = 0 ; i < prefixListSize; i++){
            String line = bufferedReader.readLine();
            prefixList.add(line);
        }
    }

    public static WeightedWord parseWeightedWordFromLine (String line){
        int separatorIndex = line.indexOf(" ");
        String word = line.substring(0, separatorIndex);
        Integer weight = Integer.parseInt(line.substring(separatorIndex + 1));
        return new WeightedWord(word,weight);
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
