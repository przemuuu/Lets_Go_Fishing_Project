package pl.edu.agh.to2.lets_go_fishing.service;

import javafx.util.Pair;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TextParserTest {
    private final TextParser textParser = new TextParser();

    @Test
    void parse_SimpleInput() {
        String input = "Hello World";

        List<Pair<String, Boolean>> result = textParser.parse(input);

        assertEquals(List.of(
                new Pair<>("Hello", false),
                new Pair<>("World", false)
        ), result);
    }

    @Test
    void parse_TextWithNumbers() {
        String inputText = "The year is 2024 and 12345 is a number.";

        List<Pair<String, Boolean>> result = textParser.parse(inputText);

        assertEquals(List.of(
                new Pair<>("The", false),
                new Pair<>("year", false),
                new Pair<>("is", false),
                new Pair<>("2024", false),
                new Pair<>("and", false),
                new Pair<>("12345", false),
                new Pair<>("is", true),
                new Pair<>("a", false),
                new Pair<>("number", false)
        ), result);
    }

    @Test
    void parse_AccentedCharacters() {
        String inputText = "Café français. Voilà! Déjà vu?";

        List<Pair<String, Boolean>> result = textParser.parse(inputText);

        assertEquals(List.of(
                new Pair<>("Café", false),
                new Pair<>("français", false),
                new Pair<>("Voilà", false),
                new Pair<>("Déjà", false),
                new Pair<>("vu", false)
        ), result);
    }

    @Test
    void parse_ChineseCharacters() {
        String inputText = "中文测试，测试成功！";

        List<Pair<String, Boolean>> result = textParser.parse(inputText);

        assertEquals(List.of(
                new Pair<>("中文测试", false),
                new Pair<>("测试成功", false)
        ), result);
    }

    @Test
    void parse_ArabicCharacters() {
        String inputText = "مرحبا بكم في عالم البرمجة.";

        List<Pair<String, Boolean>> result = textParser.parse(inputText);

        assertEquals(List.of(
                new Pair<>("مرحبا", false),
                new Pair<>("بكم", false),
                new Pair<>("في", false),
                new Pair<>("عالم", false),
                new Pair<>("البرمجة", false)
        ), result);
    }

    @Test
    void parse_HyphenatedWords() {
        String inputText = "Well-being is important.";

        List<Pair<String, Boolean>> result = textParser.parse(inputText);

        assertEquals(List.of(
                new Pair<>("Well-being", false),
                new Pair<>("is", false),
                new Pair<>("important", false)
        ), result);
    }

    @Test
    void parse_DuplicateWords() {
        String inputText = "test test test test.";

        List<Pair<String, Boolean>> result = textParser.parse(inputText);

        assertEquals(List.of(
                new Pair<>("test", false),
                new Pair<>("test", true),
                new Pair<>("test", true),
                new Pair<>("test", true)
        ), result);
    }

    @Test
    void parse_EmptyInput() {
        String inputText = "";

        List<Pair<String, Boolean>> result = textParser.parse(inputText);

        assertTrue(result.isEmpty());
    }

    @Test
    void parse_SpecialCharacters() {
        String inputText = "!@#$%^&*()_+=|}{[]:;'<>.,?/";

        List<Pair<String, Boolean>> result = textParser.parse(inputText);

        assertTrue(result.isEmpty());
    }
}