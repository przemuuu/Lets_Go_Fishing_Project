package pl.edu.agh.to2.lets_go_fishing.utils;

import pl.edu.agh.to2.lets_go_fishing.model.Flashcard;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FlashcardsSaver {

    private FlashcardsSaver() {
    }

    public static void saveToCsv(File file, List<Flashcard> flashcards) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            writer.append("Word, Translation, Part of Speech, Basic Form, Transcription\n");
            for (Flashcard flashcard : flashcards) {
                writer.append(flashcard.getWord()).append(',')
                        .append(flashcard.getPartOfSpeech()).append(',')
                        .append(flashcard.getBaseForm()).append(',')
                        .append(flashcard.getTranscription()).append(',')
                        .append(flashcard.getTranslation()).append('\n');
            }
        }
    }
}