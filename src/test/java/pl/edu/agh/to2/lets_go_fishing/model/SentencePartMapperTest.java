package pl.edu.agh.to2.lets_go_fishing.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SentencePartMapperTest {

    @Test
    void testGetPossiblePartsForNoun() {
        List<PartOfSentence> result = SentencePartMapper.getPossibleParts(PartOfSpeech.NOUN);

        List<PartOfSentence> expected = Arrays.asList(
                PartOfSentence.SUBJECT,
                PartOfSentence.DIRECT_OBJECT,
                PartOfSentence.INDIRECT_OBJECT,
                PartOfSentence.SUBJECT_COMPLEMENT,
                PartOfSentence.OBJECT_COMPLEMENT,
                PartOfSentence.APPOSITION,
                PartOfSentence.DETERMINER
        );
        assertEquals(expected, result);
    }

    @Test
    void testGetPossiblePartsForPronoun() {
        List<PartOfSentence> result = SentencePartMapper.getPossibleParts(PartOfSpeech.PRONOUN);

        List<PartOfSentence> expected = Arrays.asList(
                PartOfSentence.SUBJECT,
                PartOfSentence.DIRECT_OBJECT,
                PartOfSentence.INDIRECT_OBJECT,
                PartOfSentence.SUBJECT_COMPLEMENT,
                PartOfSentence.MODIFIER
        );
        assertEquals(expected, result);
    }

    @Test
    void testGetPossiblePartsForVerb() {
        List<PartOfSentence> result = SentencePartMapper.getPossibleParts(PartOfSpeech.VERB);

        assertEquals(Collections.singletonList(PartOfSentence.PREDICATE), result);
    }

    @Test
    void testGetPossiblePartsForAdjective() {
        List<PartOfSentence> result = SentencePartMapper.getPossibleParts(PartOfSpeech.ADJECTIVE);

        List<PartOfSentence> expected = Arrays.asList(
                PartOfSentence.MODIFIER,
                PartOfSentence.SUBJECT_COMPLEMENT,
                PartOfSentence.OBJECT_COMPLEMENT
        );
        assertEquals(expected, result);
    }

    @Test
    void testGetPossiblePartsForAdverb() {
        List<PartOfSentence> result = SentencePartMapper.getPossibleParts(PartOfSpeech.ADVERB);

        assertEquals(Collections.singletonList(PartOfSentence.MODIFIER), result);
    }

    @Test
    void testGetPossiblePartsForPreposition() {
        List<PartOfSentence> result = SentencePartMapper.getPossibleParts(PartOfSpeech.PREPOSITION);

        assertEquals(Collections.singletonList(PartOfSentence.PREPOSITION), result);
    }

    @Test
    void testGetPossiblePartsForConjunction() {
        List<PartOfSentence> result = SentencePartMapper.getPossibleParts(PartOfSpeech.CONJUNCTION);

        List<PartOfSentence> expected = Arrays.asList(
                PartOfSentence.COORDINATOR,
                PartOfSentence.SUBORDINATOR,
                PartOfSentence.CORRELATIVE
        );
        assertEquals(expected, result);
    }

    @Test
    void testGetPossiblePartsForInterjection() {
        List<PartOfSentence> result = SentencePartMapper.getPossibleParts(PartOfSpeech.INTERJECTION);

        assertEquals(Collections.singletonList(PartOfSentence.EXCLAMATION), result);
    }

    @Test
    void testGetPossiblePartsForOther() {
        List<PartOfSentence> result = SentencePartMapper.getPossibleParts(PartOfSpeech.OTHER);

        List<PartOfSentence> expected = Arrays.asList(
                PartOfSentence.SUBJECT,
                PartOfSentence.PREDICATE,
                PartOfSentence.DIRECT_OBJECT,
                PartOfSentence.INDIRECT_OBJECT,
                PartOfSentence.SUBJECT_COMPLEMENT,
                PartOfSentence.OBJECT_COMPLEMENT,
                PartOfSentence.ADVERBIAL,
                PartOfSentence.MODIFIER,
                PartOfSentence.DETERMINER,
                PartOfSentence.COORDINATOR,
                PartOfSentence.SUBORDINATOR,
                PartOfSentence.CORRELATIVE,
                PartOfSentence.PREPOSITION,
                PartOfSentence.EXCLAMATION,
                PartOfSentence.OTHER
        );
        assertEquals(expected, result);
    }

    @Test
    void testGetPossiblePartsForUnknownPartOfSpeech() {
        List<PartOfSentence> result = SentencePartMapper.getPossibleParts(null);

        assertEquals(Collections.singletonList(PartOfSentence.OTHER), result);
    }
}
