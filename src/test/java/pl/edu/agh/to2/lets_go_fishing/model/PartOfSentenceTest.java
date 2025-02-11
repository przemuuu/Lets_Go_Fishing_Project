package pl.edu.agh.to2.lets_go_fishing.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PartOfSentenceTest {

    @Test
    void testFromString_ValidInput() {
        assertEquals(PartOfSentence.SUBJECT, PartOfSentence.fromString("Subject"));
        assertEquals(PartOfSentence.PREDICATE, PartOfSentence.fromString("Predicate"));
        assertEquals(PartOfSentence.DIRECT_OBJECT, PartOfSentence.fromString("Direct object"));
        assertEquals(PartOfSentence.INDIRECT_OBJECT, PartOfSentence.fromString("Indirect object"));
        assertEquals(PartOfSentence.SUBJECT_COMPLEMENT, PartOfSentence.fromString("Subject complement"));
        assertEquals(PartOfSentence.OBJECT_COMPLEMENT, PartOfSentence.fromString("Object complement"));
        assertEquals(PartOfSentence.ADVERBIAL, PartOfSentence.fromString("Adverbial"));
        assertEquals(PartOfSentence.MODIFIER, PartOfSentence.fromString("Modifier"));
        assertEquals(PartOfSentence.DETERMINER, PartOfSentence.fromString("Determiner"));
        assertEquals(PartOfSentence.APPOSITION, PartOfSentence.fromString("Apposition"));
        assertEquals(PartOfSentence.COORDINATOR, PartOfSentence.fromString("Coordinator"));
        assertEquals(PartOfSentence.SUBORDINATOR, PartOfSentence.fromString("Subordinator"));
        assertEquals(PartOfSentence.CORRELATIVE, PartOfSentence.fromString("Correlative"));
        assertEquals(PartOfSentence.PREPOSITION, PartOfSentence.fromString("Preposition"));
        assertEquals(PartOfSentence.EXCLAMATION, PartOfSentence.fromString("Exclamation"));
        assertEquals(PartOfSentence.OTHER, PartOfSentence.fromString("Other"));
    }

    @Test
    void testFromString_InvalidInput() {
        assertNull(PartOfSentence.fromString("Non-existent"));
    }

    @Test
    void testFromString_CaseInsensitive() {
        assertEquals(PartOfSentence.SUBJECT, PartOfSentence.fromString("subject"));
        assertEquals(PartOfSentence.PREDICATE, PartOfSentence.fromString("PREDICATE"));
        assertEquals(PartOfSentence.DIRECT_OBJECT, PartOfSentence.fromString("DIRECT OBJECT"));
    }
}
