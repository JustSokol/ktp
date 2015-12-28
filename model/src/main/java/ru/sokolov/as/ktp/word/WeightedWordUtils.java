package ru.sokolov.as.ktp.word;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASokolov on 28.12.2015.
 */
public class WeightedWordUtils {

    public static List<WeightedWord> parseVocabularyFromReader(BufferedReader bufferedReader) throws IOException {
        int vocabularySize = Integer.parseInt(bufferedReader.readLine());
        List<WeightedWord> weightedWordList = new ArrayList<>(vocabularySize);
        for (int i = 0; i < vocabularySize; i++) {
            String line = bufferedReader.readLine();
            weightedWordList.add(parseWeightedWordFromLine(line));
        }
        return weightedWordList;
    }

    public static WeightedWord parseWeightedWordFromLine (String line){
        int separatorIndex = line.indexOf(" ");
        String word = line.substring(0, separatorIndex);
        Integer weight = Integer.parseInt(line.substring(separatorIndex + 1));
        return new WeightedWord(word,weight);
    }
}
