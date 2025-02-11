package pl.edu.agh.to2.lets_go_fishing.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class FlashcardsModelTest {
    @Mock
    private FlashcardsModel flashcardsModel;

    @BeforeEach
    void setUp() {
        flashcardsModel = new FlashcardsModel();
    }

    @Test
    void parseInputText_duplicates() {
        flashcardsModel.setInputText("test, testing, test");

        flashcardsModel.parseInputText();

        assertEquals(3, flashcardsModel.getTotalWords());
    }

    @Test
    void parseInputText_removesSpecialCharacters() {
        flashcardsModel.setInputText("test#!.");

        flashcardsModel.parseInputText();

        assertEquals("test", flashcardsModel.getCurrentWord());
    }

    @Test
    void getCurrentWord_singleWord() {
        flashcardsModel.setInputText("test");

        flashcardsModel.parseInputText();

        assertEquals("test", flashcardsModel.getCurrentWord());
    }

    @Test
    void nextWord_multipleWords() {
        flashcardsModel.setInputText("test, secondTest, thirdTest");

        flashcardsModel.parseInputText();
        flashcardsModel.nextWord();

        assertEquals("secondTest", flashcardsModel.getCurrentWord());
    }

    @Test
    void nextWord_noWords() {
        flashcardsModel.setInputText(" ");
        flashcardsModel.parseInputText();

        flashcardsModel.nextWord();

        assertNull(flashcardsModel.getCurrentWord());
    }

    @Test
    void nextWord_onLastWord() {
        flashcardsModel.setInputText("test, secondTest");

        flashcardsModel.parseInputText();
        flashcardsModel.nextWord();
        flashcardsModel.nextWord();
        flashcardsModel.nextWord();

        assertNull(flashcardsModel.getCurrentWord());
    }

    @Test
    void getTotalWords_shouldReturnCorrectCount() {
        flashcardsModel.setInputText("Testowa Ala ma kota, ma testowego kota.");
        flashcardsModel.parseInputText();

        assertEquals(7, flashcardsModel.getTotalWords());
    }

    @Test
    void getCurrentWordIndex_shouldReturnCorrectIndex() {
        flashcardsModel.setInputText("Testowa Ala ma kota");
        flashcardsModel.parseInputText();

        flashcardsModel.nextWord();

        assertEquals(1, flashcardsModel.getCurrentWordIndex());
    }

    @Test
    void addTranslation_singleTranslation() {
        flashcardsModel.setInputText("Kot");
        flashcardsModel.parseInputText();
        String translation = "cat";

        flashcardsModel.addTranslation(translation);

        assertEquals(1, flashcardsModel.getTranslations().size());
        assertEquals("Kot, cat, null, null, null", flashcardsModel.getTranslations().getFirst().toString());
    }
    @Test
    void addTranslation_multipleTranslations() {
        flashcardsModel.setInputText("Kot, Pies");
        flashcardsModel.parseInputText();
        flashcardsModel.addTranslation("cat");
        flashcardsModel.addTranslation("dog");

        List<Flashcard> translations = flashcardsModel.getTranslations();

        assertEquals(2, translations.size());
        assertEquals("Kot, cat, null, null, null", translations.get(0).toString());
        assertEquals("Kot, dog, null, null, null", translations.get(1).toString());
    }

    @Test
    void getTranslations() {
        flashcardsModel.setInputText("Kot, pies, małpa");
        flashcardsModel.parseInputText();

        flashcardsModel.addTranslation("cat");
        flashcardsModel.addTranslation("dog");
        flashcardsModel.addTranslation("monkey");

        List<Flashcard> translations = flashcardsModel.getTranslations();

        assertEquals(3, translations.size());
    }
}