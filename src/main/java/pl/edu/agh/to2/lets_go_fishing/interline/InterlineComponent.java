package pl.edu.agh.to2.lets_go_fishing.interline;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InterlineComponent {
    private final List<String> translatedWords;
    private final List<String> translations;
    private final List<String> transcriptions;

    public InterlineComponent() {
        this.translatedWords = new ArrayList<>();
        this.translations = new ArrayList<>();
        this.transcriptions = new ArrayList<>();
    }

    public void addWord(String translatedWord, String translation, String transcription) {
        this.translatedWords.add(translatedWord);
        this.translations.add(translation);
        this.transcriptions.add(transcription);
    }

    public List<String> getTranslatedWords() {
        return translatedWords;
    }

    public List<String> getTranslations() {
        return translations;
    }

    public List<String> getTranscriptions() {
        return transcriptions;
    }

    public void clear() {
        translatedWords.clear();
        translations.clear();
        transcriptions.clear();
    }
}
