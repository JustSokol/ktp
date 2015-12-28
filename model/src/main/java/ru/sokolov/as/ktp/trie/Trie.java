package ru.sokolov.as.ktp.trie;

import ru.sokolov.as.ktp.word.WeightedWord;

import java.util.*;

/**
 * Created by ASokolov on 24.12.2015.
 */
public class Trie {

    private static final int PREFIX_SEARCH_DEFAULT_LIMIT = 10;

    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public Trie(TrieNode root) {
        this.root = root;
    }

    public void addWord(WeightedWord weightedWord) {

        TrieNode trieNode = root;
        Map<Character, TrieNode> children;
        String word = weightedWord.getWord();

        for (int i = 0; i < word.length(); i++) {
            char charAt = word.charAt(i);
            children = trieNode.getChildren();
            trieNode.getContainedWords().add(weightedWord);

            if (children.containsKey(charAt)) {
                trieNode = children.get(charAt);
            } else {
                trieNode = new TrieNode();
                trieNode.setCharacter(charAt);
                children.put(charAt, trieNode);
            }
        }

        trieNode.getContainedWords().add(weightedWord);
        trieNode.setIsWord(true);
    }

    public void fillWithWeightedWords(Collection<WeightedWord> collection) {
        for (WeightedWord weightedWord : collection) {
            addWord(weightedWord);
        }
    }

    /**
     * @param prefix
     * @return null if no words with this prefix in vocabulary;
     */

    public SortedSet<WeightedWord> searchWordsByPrefix(String prefix) {

        TrieNode trieNode = root;

        for (int i = 0; i < prefix.length(); i++) {
            char charAt = prefix.charAt(i);
            trieNode = trieNode.getChildren().get(charAt);
            if (trieNode == null)
                return null;
        }
        return trieNode.getContainedWords();
    }

    public List<WeightedWord> searchLimitedWordsByPrefix(String prefix) {
        return searchLimitedWordsByPrefix(prefix, PREFIX_SEARCH_DEFAULT_LIMIT);
    }

    public List<WeightedWord> searchLimitedWordsByPrefix(String prefix, int limit) {
        ArrayList<WeightedWord> result = new ArrayList<>(limit);
        SortedSet<WeightedWord> weightedWords = searchWordsByPrefix(prefix);
        if (weightedWords == null) {
            return null;
        }
        Iterator<WeightedWord> iterator = weightedWords.iterator();
        for (int i = 0; i < limit && iterator.hasNext(); i++) {
            result.add(iterator.next());
        }
        return result;
    }

    public TrieNode getRoot() {
        return root;
    }

    public void setRoot(TrieNode root) {
        this.root = root;
    }
}
