package pl.edu.agh.to2.lets_go_fishing.service;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SentenceParserTest {

    @Test
    void testParseSingleSentence() {
        SentenceParser parser = new SentenceParser();
        String inputText = "This is a test sentence.";

        List<Integer> wordCounts = parser.parse(inputText);

        assertEquals(1, wordCounts.size());
        assertEquals(5, wordCounts.getFirst());
    }

    @Test
    void testParseMultipleSentences() {
        SentenceParser parser = new SentenceParser();
        String inputText = "First sentence. Second sentence has more words.";

        List<Integer> wordCounts = parser.parse(inputText);

        assertEquals(2, wordCounts.size());
        assertEquals(2, wordCounts.get(0));
        assertEquals(5, wordCounts.get(1));
    }

    @Test
    void testParseEmptyString() {
        SentenceParser parser = new SentenceParser();
        String inputText = "";

        List<Integer> wordCounts = parser.parse(inputText);

        assertTrue(wordCounts.isEmpty());
    }

    @Test
    void testParseNoWords() {
        SentenceParser parser = new SentenceParser();
        String inputText = "....!?";

        List<Integer> wordCounts = parser.parse(inputText);

        assertTrue(wordCounts.isEmpty());
    }

    @Test
    void testParseMixedContent() {
        SentenceParser parser = new SentenceParser();
        String inputText = "Numbers 123 and words in 1 sentence.";

        List<Integer> wordCounts = parser.parse(inputText);

        assertEquals(1, wordCounts.size());
        assertEquals(7, wordCounts.getFirst());
    }
}
