package pl.edu.agh.to2.lets_go_fishing.service;

import org.springframework.stereotype.Service;
import pl.edu.agh.to2.lets_go_fishing.interline.InterlineComponent;
import pl.edu.agh.to2.lets_go_fishing.model.SentenceSplit;

import java.util.ArrayList;
import java.util.List;

@Service
public class InterlineService {
    private static final int MAX_LINE_WIDTH = 75;
    private final InterlineComponent interlineComponent;
    private List<SentenceSplit> sentenceSplits;

    public InterlineService(InterlineComponent interlineComponent) {
        this.interlineComponent = interlineComponent;
        this.sentenceSplits = new ArrayList<>();
    }

    public void addWord(String translatedWord, String translation, String transcription) {
        interlineComponent.addWord(translatedWord, translation, transcription);
    }

    public void addSentenceSplit(SentenceSplit sentenceSplit) {
        sentenceSplits.add(sentenceSplit);
    }


    public String getInterlinedTranslation() {
        List<String> translatedWords = interlineComponent.getTranslatedWords();
        List<String> translations = interlineComponent.getTranslations();
        List<String> transcriptions = interlineComponent.getTranscriptions();
        List<Integer> columnWidths = calculateMaxColumnWidths(translatedWords, translations, transcriptions);
        List<Integer> wordsPerLine = calculateMaxWordsPerLine(translatedWords, columnWidths);

        return buildInterlinedTranslation(translatedWords, translations, transcriptions, columnWidths, wordsPerLine);
    }

    private List<Integer> calculateMaxWordsPerLine(List<String> words, List<Integer> columnWidths) {
        int currentLineWidth = 0;
        int counter = 0;
        int sentenceIndex = 0;
        int sentenceFilled = 0;
        List<Integer> widths = new ArrayList<>();
        for (int i = 0; i < words.size(); i++) {
            if(counter == sentenceSplits.get(sentenceIndex).getSplitWordNumber() && sentenceFilled == 0) {
                widths.add(counter);
                counter = 0;
                currentLineWidth = 0;
                sentenceFilled = 1;
            }
            else if (currentLineWidth + columnWidths.get(i) > MAX_LINE_WIDTH) {
                widths.add(counter);
                counter = 0;
                currentLineWidth = 0;
            }
            currentLineWidth += columnWidths.get(i);
            counter += 1;
            if(words.get(i).endsWith(".") || words.get(i).endsWith("!") || words.get(i).endsWith("?")) {
                widths.add(counter);
                sentenceIndex += 1;
                sentenceFilled = 0;
                counter = 0;
                currentLineWidth = 0;
            }
        }
        if(counter > 0) {
            widths.add(counter);
        }
        return widths;
    }

    private List<Integer> calculateMaxColumnWidths(List<String> translatedWords, List<String> translations, List<String> transcriptions) {
        List<Integer> activeWidth = new ArrayList<>();
        for (int i = 0; i < translatedWords.size(); i++) {
            int maxLength = Math.max(translatedWords.get(i).length(), translations.get(i).length());
            maxLength = Math.max(maxLength, transcriptions.get(i).length());
            activeWidth.add(maxLength + 3);
        }
        return activeWidth;
    }

    private String formatWord(String word, int width) {
        return word + " ".repeat(Math.max(0, width - word.length()));
    }

    private String formatLine(List<String> words, List<Integer> columnWidths, int currentWord, int numberOfWords, int sentenceIndex, int isFirstLine) {
        int firstWord = 1;
        StringBuilder line = new StringBuilder();
        for (int i = currentWord; i < currentWord + numberOfWords; i++) {
            if(sentenceSplits.get(sentenceIndex).getSplitFlag() == 1 && isFirstLine == 0 && firstWord == 1) {
                line.append("          ");
                firstWord = 0;
            }
            line.append(formatWord(words.get(i), columnWidths.get(i)));
        }
        line.append("\n");
        return line.toString();
    }

    private String buildInterlinedTranslation(List<String> translatedWords, List<String> translations, List<String> transcriptions, List<Integer> columnWidths, List<Integer> wordsPerLine) {
        int currentWord = 0;
        int sentenceIndex = 0;
        int isFirstLine = 1;
        StringBuilder interlinedTranslation = new StringBuilder();
        for (int numberOfWords : wordsPerLine) {
            interlinedTranslation
                    .append(formatLine(translatedWords, columnWidths, currentWord, numberOfWords, sentenceIndex, isFirstLine))
                    .append(formatLine(translations, columnWidths, currentWord, numberOfWords, sentenceIndex, isFirstLine))
                    .append(formatLine(transcriptions, columnWidths, currentWord, numberOfWords, sentenceIndex, isFirstLine))
                    .append("\n");
            if(translatedWords.get(currentWord + numberOfWords - 1).endsWith(".") || translatedWords.get(currentWord + numberOfWords - 1).endsWith("!") || translatedWords.get(currentWord + numberOfWords - 1).endsWith("?")) {
                interlinedTranslation.append("\n");
                sentenceIndex += 1;
                isFirstLine = 1;
            } else {
                isFirstLine = 0;
            }
            currentWord += numberOfWords;
        }
        return interlinedTranslation.toString().trim();
    }

    public void clear() {
        interlineComponent.clear();
    }
}
