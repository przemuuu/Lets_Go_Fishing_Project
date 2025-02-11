package pl.edu.agh.to2.lets_go_fishing.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SentenceTest {

    @Test
    void testConstructorAndGetId() {
        Integer id = 1;

        Sentence sentence = new Sentence(id);

        assertEquals(id, sentence.getId());
    }

    @Test
    void testGetAndSetWords() {
        Sentence sentence = new Sentence(1);
        List<WordPart> words = List.of(
                new WordPart("Hello", PartOfSentence.PREDICATE),
                new WordPart("World", PartOfSentence.COORDINATOR)
        );

        sentence.setWords(words);

        assertEquals(words, sentence.getWords());
    }

    @Test
    void testAddWord() {
        Sentence sentence = new Sentence(1);
        String word1 = "Hello";
        PartOfSentence partOfSentence1 = PartOfSentence.PREDICATE;
        String word2 = "World";
        PartOfSentence partOfSentence2 = PartOfSentence.COORDINATOR;

        sentence.addWord(word1, partOfSentence1);
        sentence.addWord(word2, partOfSentence2);

        List<WordPart> words = sentence.getWords();
        assertEquals(2, words.size());
    }

    @Test
    void testEmptyWordsListInitially() {
        Sentence sentence = new Sentence(1);

        assertTrue(sentence.getWords().isEmpty());
    }
}
