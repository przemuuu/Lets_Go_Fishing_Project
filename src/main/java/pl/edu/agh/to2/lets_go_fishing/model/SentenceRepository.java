package pl.edu.agh.to2.lets_go_fishing.model;

import java.util.ArrayList;
import java.util.List;

public class SentenceRepository {
    private final List<Sentence> sentences = new ArrayList<>();

    public void addSentence(Sentence sentence) {
        sentences.add(sentence);
    }

    public void addWordToSentence(int sentenceId, String word, PartOfSentence partOfSentence) {
        for (Sentence sentence : sentences) {
            if (sentence.getId().equals(sentenceId)) {
                sentence.addWord(word, partOfSentence);
                return;
            }
        }
    }

    public void clear() {
        sentences.clear();
    }

    public List<Sentence> getAllSentences() {
        return new ArrayList<>(sentences);
    }
}