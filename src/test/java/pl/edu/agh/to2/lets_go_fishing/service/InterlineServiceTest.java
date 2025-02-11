package pl.edu.agh.to2.lets_go_fishing.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.edu.agh.to2.lets_go_fishing.interline.InterlineComponent;
import pl.edu.agh.to2.lets_go_fishing.model.SentenceSplit;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InterlineServiceTest {

    @Mock
    private InterlineComponent interlineComponent;

    private InterlineService interlineService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        interlineService = new InterlineService(interlineComponent);
    }

    @Test
    void shouldDelegateAddWordToComponent() {
        String translatedWord = "hello";
        String translation = "cześć";
        String transcription = "həˈləʊ";

        interlineService.addWord(translatedWord, translation, transcription);

        verify(interlineComponent).addWord(translatedWord, translation, transcription);
    }

    @Test
    void shouldDelegateClearToComponent() {
        interlineService.clear();

        verify(interlineComponent).clear();
    }

    @Test
    void shouldHandleMultipleWordsInSingleLine() {
        List<String> translatedWords = Arrays.asList("cat", "dog");
        List<String> translations = Arrays.asList("kot", "pies");
        List<String> transcriptions = Arrays.asList("kæt", "dɒɡ");

        when(interlineComponent.getTranslatedWords()).thenReturn(translatedWords);
        when(interlineComponent.getTranslations()).thenReturn(translations);
        when(interlineComponent.getTranscriptions()).thenReturn(transcriptions);

        interlineService.addSentenceSplit(new SentenceSplit(2, 0));
        String result = interlineService.getInterlinedTranslation();

        String expected =
                "cat   dog    \n" +
                        "kot   pies   \n" +
                        "kæt   dɒɡ";
        assertEquals(expected, result);
    }

    @Test
    void shouldHandleEmptyLists() {
        List<String> emptyList = List.of();
        when(interlineComponent.getTranslatedWords()).thenReturn(emptyList);
        when(interlineComponent.getTranslations()).thenReturn(emptyList);
        when(interlineComponent.getTranscriptions()).thenReturn(emptyList);

        String result = interlineService.getInterlinedTranslation();

        assertEquals("", result);
    }

    @Test
    void shouldHandleDifferentWordLengths() {
        List<String> translatedWords = Arrays.asList("short", "verylongword");
        List<String> translations = Arrays.asList("krótkie", "bardzodługie");
        List<String> transcriptions = Arrays.asList("ʃɔːt", "verylongwordtrans");

        when(interlineComponent.getTranslatedWords()).thenReturn(translatedWords);
        when(interlineComponent.getTranslations()).thenReturn(translations);
        when(interlineComponent.getTranscriptions()).thenReturn(transcriptions);

        interlineService.addSentenceSplit(new SentenceSplit(1, 0));
        String result = interlineService.getInterlinedTranslation();

        String[] lines = result.split("\n");
        assertTrue(lines[0].length() >= translatedWords.get(0).length());
        assertFalse(lines[0].length() >= translatedWords.get(1).length());
    }

    @Test
    void shouldAlignColumnsCorrectly() {
        List<String> translatedWords = Arrays.asList("big", "small");
        List<String> translations = Arrays.asList("duży", "mały");
        List<String> transcriptions = Arrays.asList("bɪɡ", "smɔːl");

        when(interlineComponent.getTranslatedWords()).thenReturn(translatedWords);
        when(interlineComponent.getTranslations()).thenReturn(translations);
        when(interlineComponent.getTranscriptions()).thenReturn(transcriptions);

        interlineService.addSentenceSplit(new SentenceSplit(1, 0));
        String result = interlineService.getInterlinedTranslation();

        String[] lines = result.split("\n");
        int firstWordEndPos = lines[0].indexOf("small");
        assertFalse(firstWordEndPos > 0);
        assertEquals(firstWordEndPos, lines[1].indexOf("mały"));
        assertEquals(firstWordEndPos, lines[2].indexOf("smɔːl"));
    }
}