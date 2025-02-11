package pl.edu.agh.to2.lets_go_fishing.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class FlashcardTest {

    @Test
    void createFlashcardFromValidData() {
        String word = "cat";
        String translation = "kot";

        Flashcard flashcard = new Flashcard(word, translation);

        assertEquals(word, flashcard.getWord());
        assertEquals(translation, flashcard.getTranslation());
    }

    @Test
    void testToString() {
        Flashcard flashcard = new Flashcard("cat", "kot");

        String result = flashcard.toString();

        assertEquals("cat, kot, null, null, null", result);
    }

    @Test
    void createFlashcardWithAllFields() {
        String word = "run";
        String translation = "bieg";
        String partOfSpeech = "verb";
        String baseForm = "run";
        String transcription = "/rʌn/";

        Flashcard flashcard = new Flashcard(word, translation, partOfSpeech, baseForm, transcription);

        assertEquals(word, flashcard.getWord());
        assertEquals(translation, flashcard.getTranslation());
        assertEquals(partOfSpeech, flashcard.getPartOfSpeech());
        assertEquals(baseForm, flashcard.getBaseForm());
        assertEquals(transcription, flashcard.getTranscription());
    }


    @Test
    void createWithNullValues() {
        Flashcard flashcard = new Flashcard(null, null);

        assertNull(flashcard.getWord());
        assertNull(flashcard.getTranslation());
        assertNull(flashcard.getPartOfSpeech());
        assertNull(flashcard.getBaseForm());
        assertNull(flashcard.getTranscription());
        assertEquals("null, null, null, null, null", flashcard.toString());
    }
}