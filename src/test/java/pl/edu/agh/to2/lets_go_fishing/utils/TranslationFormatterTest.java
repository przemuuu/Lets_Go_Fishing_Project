package pl.edu.agh.to2.lets_go_fishing.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TranslationFormatterTest {

    @Test
    void formatTranslation() {
        String formatted = TranslationFormatter.formatTranslation("Word", "Translation");
        assertEquals("Word, Translation", formatted);
    }
}