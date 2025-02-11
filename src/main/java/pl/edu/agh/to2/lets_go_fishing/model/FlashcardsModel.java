package pl.edu.agh.to2.lets_go_fishing.model;

import org.springframework.stereotype.Component;
import pl.edu.agh.to2.lets_go_fishing.service.TextParser;

import javafx.util.Pair;
import java.util.List;


@Component
public class FlashcardsModel {

    private String inputText;
    private final TextParser textParser;
    private final FlashcardsRepository repository;
    private List<Pair<String, Boolean>> words;
    private int currentWordIndex;

    public FlashcardsModel() {
        this.textParser = new TextParser();
        this.repository = new FlashcardsRepository();
    }


    public void parseInputText() {
        repository.clear();
        this.words = textParser.parse(inputText);
        this.currentWordIndex = 0;
    }

    public String getCurrentWord() {
        return currentWordIndex < words.size() ? words.get(currentWordIndex).getKey() : null;
    }

    public boolean isCurrentWordDuplicate() {
        return currentWordIndex < words.size() && words.get(currentWordIndex).getValue();
    }

    public void addTranslation(String translation) {
        String word = words.get(currentWordIndex).getKey();
        repository.addFlashcard(new Flashcard(word, translation));
    }

    public void addTranslation(String translation, String partOfSpeech, String baseForm, String transcription) {
        String word = words.get(currentWordIndex).getKey();
        repository.addFlashcard(new Flashcard(word, translation, partOfSpeech, baseForm, transcription));
    }

    public void nextWord() {
        if (currentWordIndex < words.size()) {
            currentWordIndex++;
        }
    }

    public int getTotalWords() {
        return words == null ? 0 : words.size();
    }

    public int getCurrentWordIndex() {
        return currentWordIndex;
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
        this.currentWordIndex = 0;
        repository.clear();
    }

    public List<Flashcard> getTranslations() {
        return repository.getAllFlashcards();
    }
}
