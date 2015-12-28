package ru.sokolov.as.ktp.word;

/**
 * Created by ASokolov on 24.12.2015.
 */
public class WeightedWord implements Comparable {

    private String word;

    // Частота с которой встречается слово.
    private Integer weight;

    public WeightedWord() {
    }

    public WeightedWord(String word, Integer weight) {
        this.word = word;
        this.weight = weight;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    /**
     * Порядок сравнения - убыванию частоты, в случае одинаковых частот - лекскографически по слову.
     */
    @Override
    public int compareTo(Object o) {
        WeightedWord weightedWord = (WeightedWord) o;
        int integerComparison = this.weight.compareTo(weightedWord.getWeight());
        if (integerComparison != 0 ) {
            // Знак минус - для того чтобы порядок по умолчанию был от больших частот к меньшим.
            return -(integerComparison);
        }
        return word.compareTo(weightedWord.getWord());
    }
}
