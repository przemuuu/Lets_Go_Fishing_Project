package pl.edu.agh.to2.lets_go_fishing.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SentenceRepositoryTest {

    @Test
    void testAddSentence() {
        SentenceRepository repository = new SentenceRepository();
        Sentence sentence = new Sentence(1);

        repository.addSentence(sentence);

        List<Sentence> sentences = repository.getAllSentences();
        assertEquals(1, sentences.size());
        assertEquals(sentence, sentences.getFirst());
    }

    @Test
    void testAddWordToSentence() {
        SentenceRepository repository = new SentenceRepository();
        Sentence sentence = new Sentence(1);
        repository.addSentence(sentence);

        repository.addWordToSentence(1, "word", PartOfSentence.SUBJECT);

        List<Sentence> sentences = repository.getAllSentences();
        assertEquals(1, sentences.size());
        assertEquals(1, sentences.getFirst().getWords().size());
        assertEquals("word", sentences.getFirst().getWords().getFirst().getWord());
        assertEquals(PartOfSentence.SUBJECT, sentences.getFirst().getWords().getFirst().getPartOfSentence());
    }

    @Test
    void testAddWordToNonExistentSentence() {
        SentenceRepository repository = new SentenceRepository();

        repository.addWordToSentence(99, "word", PartOfSentence.SUBJECT);

        List<Sentence> sentences = repository.getAllSentences();
        assertTrue(sentences.isEmpty());
    }

    @Test
    void testClear() {
        SentenceRepository repository = new SentenceRepository();
        Sentence sentence1 = new Sentence(1);
        Sentence sentence2 = new Sentence(2);
        repository.addSentence(sentence1);
        repository.addSentence(sentence2);

        repository.clear();

        List<Sentence> sentences = repository.getAllSentences();
        assertTrue(sentences.isEmpty());
    }

    @Test
    void testGetAllSentencesReturnsCopy() {
        SentenceRepository repository = new SentenceRepository();
        Sentence sentence = new Sentence(1);
        repository.addSentence(sentence);

        List<Sentence> sentences = repository.getAllSentences();
        sentences.clear();

        assertEquals(1, repository.getAllSentences().size());
    }
}
