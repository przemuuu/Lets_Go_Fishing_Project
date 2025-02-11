package pl.edu.agh.to2.lets_go_fishing.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.to2.lets_go_fishing.model.PartOfSentence;
import pl.edu.agh.to2.lets_go_fishing.model.SentenceModel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SentenceServiceTest {

    private SentenceService sentenceService;

    @BeforeEach
    void setUp() {
        sentenceService = new SentenceService();

    }

    @Test
    void testGetModelReturnsNonNull() {
        SentenceModel model = sentenceService.getModel();

        assertNotNull(model);
    }

    @Test
    void testAddMultipleWordsToSentence() {
        SentenceModel model = sentenceService.getModel();
        model.setInputText("first. second");
        model.parseInputText();
        sentenceService.addWordToSentence("first", PartOfSentence.SUBJECT);
        sentenceService.addWordToSentence("second", PartOfSentence.PREDICATE);

        assertEquals(2, model.getAllSentences().size());
        assertEquals("first", model.getAllSentences().get(0).getWords().getFirst().getWord());
        assertEquals(PartOfSentence.SUBJECT, model.getAllSentences().get(0).getWords().getFirst().getPartOfSentence());
        assertEquals("second", model.getAllSentences().get(1).getWords().getFirst().getWord());
    }

    @Test
    void testAddWordToSentenceWithNullPartOfSentence() {
        assertThrows(NullPointerException.class, () ->
                sentenceService.addWordToSentence("nullPart", null)
        );
    }
}
