package pl.edu.agh.to2.lets_go_fishing.model;

import pl.edu.agh.to2.lets_go_fishing.service.SentenceParser;

import java.util.List;

public class SentenceModel {

    private String inputText;
    private final SentenceRepository repository;
    private final SentenceParser parser;
    private List<Integer> wordCounts;
    private int currentSentenceIndex;
    private Sentence currentSentence;

    public SentenceModel() {
        this.repository = new SentenceRepository();
        this.parser = new SentenceParser();
    }

    public void parseInputText() {
        repository.clear();
        this.wordCounts = parser.parse(inputText);
        this.currentSentenceIndex = 0;
        createNewSentence();
    }

    private void createNewSentence() {
        currentSentence = new Sentence(currentSentenceIndex + 1);
    }

    public void addWordToSentence(String word, PartOfSentence partOfSentence) {
        currentSentence.addWord(word, partOfSentence);
        if (currentSentence.getWords().size() == getCurrentSentenceWordCount()) {
            repository.addSentence(currentSentence);
            nextSentence();
        }
    }

    public int getCurrentSentenceWordCount() {
        return currentSentenceIndex < wordCounts.size() ? wordCounts.get(currentSentenceIndex) : 0;
    }

    public void nextSentence() {
        currentSentenceIndex++;
        createNewSentence();
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
        this.currentSentenceIndex = 0;
        repository.clear();
    }

    public List<Sentence> getAllSentences() {
        return repository.getAllSentences();
    }
}