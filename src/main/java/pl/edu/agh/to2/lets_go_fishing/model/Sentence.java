package pl.edu.agh.to2.lets_go_fishing.model;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Sentence {
    private final Integer id;
    private List<WordPart> words = new ArrayList<>();

    public Sentence(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public List<WordPart> getWords() {
        return words;
    }

    public void setWords(List<WordPart> words) {
        this.words = words;
    }

    public void addWord(String word, PartOfSentence partOfSentence) {
        words.add(new WordPart(word, partOfSentence));
    }

}
