package pl.edu.agh.to2.lets_go_fishing.utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.to2.lets_go_fishing.model.Flashcard;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class FlashcardsSaverTest {

    private File tempFile;

    @BeforeEach
    void setUp() throws IOException {
        tempFile = File.createTempFile("flashcards", ".csv");
        tempFile.deleteOnExit();
    }

    @Test
    void testSaveToCsvSuccess() throws IOException {

        List<Flashcard> flashcards = new ArrayList<>();
        flashcards.add(new Flashcard("word1", "translation1", "noun", "word1_base", "/wɜːd1/"));
        flashcards.add(new Flashcard("word2", "translation2", "verb", "word2_base", "/wɜːd2/"));


        FlashcardsSaver.saveToCsv(tempFile, flashcards);

        String content = new String(Files.readAllBytes(tempFile.toPath())).trim();

        String expectedContent = """
                Word, Translation, Part of Speech, Basic Form, Transcription
                word1, noun, word1_base, /wɜːd1/, translation1
                word2, verb, word2_base, /wɜːd2/, translation2""";

        assertNotEquals(expectedContent, content);
    }
}
