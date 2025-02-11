package pl.edu.agh.to2.lets_go_fishing.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InputValidatorTest {

    @Test
    void isValidInput_TrueForValidInput() {
        assertTrue(InputValidator.isValidInput("test123"));
    }

    @Test
    void isValidInput_FalseForEmptyInput() {
        assertFalse(InputValidator.isValidInput(""));
    }

    @Test
    void isValidInput_FalseForWhiteSpacesInput() {
        assertFalse(InputValidator.isValidInput("  "));
    }

    @Test
    void isValidInput_TooLongInput() {
        String longString = "x".repeat(10_000);
        assertTrue(InputValidator.isValidInput(longString));
    }

    @Test
    void isValidInput_HebrewVowels() {
        assertTrue(InputValidator.isValidInput("אַמְרָפֶ֣ל  מֶֽלֶךְ־ שִׁנְעָ֔ר"));
    }

    @Test
    void isValidInput_WordsSeparatedBySpaces() {
        assertTrue(InputValidator.isValidInput("word anotherWord"));
    }

    @Test
    void isValidInput_WordsSeparatedByCommas() {
        assertTrue(InputValidator.isValidInput("word,anotherWord"));
    }

    @Test
    void isValidInput_WordsSeparatedByMixedDelimiters() {
        assertTrue(InputValidator.isValidInput("word, anotherWord;yetAnother"));
    }
}