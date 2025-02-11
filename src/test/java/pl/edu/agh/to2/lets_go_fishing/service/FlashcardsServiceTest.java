package pl.edu.agh.to2.lets_go_fishing.service;

import org.junit.jupiter.api.Test;
import pl.edu.agh.to2.lets_go_fishing.model.FlashcardsModel;

import static org.junit.jupiter.api.Assertions.assertEquals;


class FlashcardsServiceTest {

    private final FlashcardsService flashcardsService = new FlashcardsService();

    @Test
    void addTranslation() {
        String word = "hello";
        String translation = "cześć";
        FlashcardsModel flashcardsModel = flashcardsService.getModel();

        flashcardsModel.setInputText(word);
        flashcardsModel.parseInputText();
        flashcardsService.addTranslation(word, translation);

        assertEquals("hello, hello, cześć, null, null, null", flashcardsModel.getTranslations().getFirst().toString());
    }
}