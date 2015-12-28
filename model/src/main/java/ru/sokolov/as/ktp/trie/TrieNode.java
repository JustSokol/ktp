package ru.sokolov.as.ktp.trie;

import ru.sokolov.as.ktp.word.WeightedWord;

import java.util.*;

/**
 * Created by ASokolov on 24.12.2015.
 */
public class TrieNode {

    private Character character;
    private Map<Character, TrieNode> children = new HashMap<>();
    private Boolean isWord = false;

    /**
     * —писок слов, префиксом которых €вл€етс€ данный нод.
     * <br> јвтоматически происходит сортировка вставкой на основе TreeSet, пор€док - по убыванию частоты
     */
    private SortedSet<WeightedWord> containedWords = new TreeSet<>();


    public TrieNode() {
    }

    public TrieNode(Character character) {
        this.character = character;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public Map<Character, TrieNode> getChildren() {
        return children;
    }

    public void setChildren(Map<Character, TrieNode> children) {
        this.children = children;
    }

    public Boolean getIsWord() {
        return isWord;
    }

    public void setIsWord(Boolean isWord) {
        this.isWord = isWord;
    }

    public Boolean getHasChildren() {
        return !children.isEmpty();
    }

    public SortedSet<WeightedWord> getContainedWords() {
        return containedWords;
    }

    public void setContainedWords(SortedSet<WeightedWord> containedWords) {
        this.containedWords = containedWords;
    }
}
