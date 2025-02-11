package pl.edu.agh.to2.lets_go_fishing.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class WordPartTest {

    @Test
    void constructorShouldInitializeFieldsCorrectly() {
        String expectedWord = "running";
        PartOfSentence expectedPart = PartOfSentence.APPOSITION;

        WordPart wordPart = new WordPart(expectedWord, expectedPart);

        assertEquals(expectedWord, wordPart.getWord());
        assertEquals(expectedPart, wordPart.getPartOfSentence());
    }

    @Test
    void setWordShouldUpdateWordValue() {
        WordPart wordPart = new WordPart("initial", PartOfSentence.APPOSITION);
        String newWord = "updated";

        wordPart.setWord(newWord);

        assertEquals(newWord, wordPart.getWord());
    }

    @Test
    void setPartOfSentenceShouldUpdatePartValue() {
        WordPart wordPart = new WordPart("happy", PartOfSentence.APPOSITION);
        PartOfSentence newPart = PartOfSentence.APPOSITION;

        wordPart.setPartOfSentence(newPart);

        assertEquals(newPart, wordPart.getPartOfSentence());
    }

    @Test
    void gettersShouldReturnCorrectValues() {
        String expectedWord = "quickly";
        PartOfSentence expectedPart = PartOfSentence.APPOSITION;
        WordPart wordPart = new WordPart(expectedWord, expectedPart);

        String actualWord = wordPart.getWord();
        PartOfSentence actualPart = wordPart.getPartOfSentence();

        assertEquals(expectedWord, actualWord);
        assertEquals(expectedPart, actualPart);
    }

    @Test
    void shouldAllowNullWord() {
        WordPart wordPart = new WordPart(null, PartOfSentence.APPOSITION);

        String word = wordPart.getWord();

        assertNull(word);
    }

    @Test
    void shouldAllowUpdatingToNullWord() {
        WordPart wordPart = new WordPart("test", PartOfSentence.APPOSITION);

        wordPart.setWord(null);

        assertNull(wordPart.getWord());
    }
}