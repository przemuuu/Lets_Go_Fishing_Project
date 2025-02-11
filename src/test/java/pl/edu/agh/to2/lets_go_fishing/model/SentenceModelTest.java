package pl.edu.agh.to2.lets_go_fishing.model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.to2.lets_go_fishing.service.SentenceParser;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SentenceModelTest {

    private SentenceModel sentenceModel;
    private SentenceParser mockParser;

    @BeforeEach
    void setUp() {
        mockParser = mock(SentenceParser.class);
        sentenceModel = new SentenceModel();
    }

    @Test
    void testParseInputText() {
        String inputText = "This is a test. It has two sentences.";
        List<Integer> expectedWordCounts = Arrays.asList(4, 4);
        when(mockParser.parse(inputText)).thenReturn(expectedWordCounts);

        sentenceModel.setInputText(inputText);

        sentenceModel.parseInputText();

        assertEquals(4, sentenceModel.getCurrentSentenceWordCount());
    }

    @Test
    void testAddWordToSentenceAndNextSentence() {
        sentenceModel.setInputText("This is a test.");
        when(mockParser.parse("This is a test.")).thenReturn(List.of(4));
        sentenceModel.parseInputText();

        sentenceModel.addWordToSentence("This", PartOfSentence.SUBJECT);
        sentenceModel.addWordToSentence("is", PartOfSentence.PREDICATE);
        sentenceModel.addWordToSentence("a", PartOfSentence.DETERMINER);
        sentenceModel.addWordToSentence("test", PartOfSentence.DETERMINER);

        List<Sentence> sentences = sentenceModel.getAllSentences();
        assertEquals(1, sentences.size());
        assertEquals(4, sentences.getFirst().getWords().size());
    }

    @Test
    void testGetAllSentences() {
        sentenceModel.setInputText("Sentence one. Sentence two.");
        when(mockParser.parse("Sentence one. Sentence two.")).thenReturn(Arrays.asList(2, 2));
        sentenceModel.parseInputText();

        sentenceModel.addWordToSentence("Sentence", PartOfSentence.SUBJECT);
        sentenceModel.addWordToSentence("one", PartOfSentence.PREDICATE);

        sentenceModel.addWordToSentence("Sentence", PartOfSentence.SUBJECT);
        sentenceModel.addWordToSentence("two", PartOfSentence.DETERMINER);

        List<Sentence> sentences = sentenceModel.getAllSentences();

        assertEquals(2, sentences.size());
        assertEquals(2, sentences.get(0).getWords().size());
        assertEquals(2, sentences.get(1).getWords().size());
    }

    @Test
    void testSetInputTextClearsRepository() {
        sentenceModel.setInputText("Old text.");
        when(mockParser.parse("Old text.")).thenReturn(List.of(2));
        sentenceModel.parseInputText();
        sentenceModel.addWordToSentence("Old", PartOfSentence.SUBJECT);
        sentenceModel.addWordToSentence("text", PartOfSentence.PREDICATE);

        sentenceModel.setInputText("New text.");

        assertTrue(sentenceModel.getAllSentences().isEmpty());
    }

    @Test
    void testNextSentenceHandlesEmptyWordCounts() {
        sentenceModel.setInputText("Empty.");
        when(mockParser.parse("Empty.")).thenReturn(List.of());
        sentenceModel.parseInputText();

        assertDoesNotThrow(() -> sentenceModel.nextSentence());
    }
}

