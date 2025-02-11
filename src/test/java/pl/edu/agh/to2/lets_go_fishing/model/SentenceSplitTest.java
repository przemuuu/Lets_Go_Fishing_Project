package pl.edu.agh.to2.lets_go_fishing.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SentenceSplitTest {

    @Test
    void constructorInitializesFieldsCorrectly() {
        SentenceSplit sentenceSplit = new SentenceSplit(5, 1);
        assertEquals(5, sentenceSplit.getSplitWordNumber());
        assertEquals(1, sentenceSplit.getSplitFlag());
    }

    @Test
    void getSplitWordNumberReturnsCorrectValue() {
        SentenceSplit sentenceSplit = new SentenceSplit(3, 0);
        assertEquals(3, sentenceSplit.getSplitWordNumber());
    }

    @Test
    void setSplitWordNumberUpdatesValue() {
        SentenceSplit sentenceSplit = new SentenceSplit(3, 0);
        sentenceSplit.setSplitWordNumber(7);
        assertEquals(7, sentenceSplit.getSplitWordNumber());
    }

    @Test
    void getSplitFlagReturnsCorrectValue() {
        SentenceSplit sentenceSplit = new SentenceSplit(3, 0);
        assertEquals(0, sentenceSplit.getSplitFlag());
    }

    @Test
    void setSplitFlagUpdatesValue() {
        SentenceSplit sentenceSplit = new SentenceSplit(3, 0);
        sentenceSplit.setSplitFlag(2);
        assertEquals(2, sentenceSplit.getSplitFlag());
    }

    @Test
    void splitWordNumberCanBeZero() {
        SentenceSplit sentenceSplit = new SentenceSplit(0, 1);
        assertEquals(0, sentenceSplit.getSplitWordNumber());
    }

    @Test
    void splitFlagCanBeNegative() {
        SentenceSplit sentenceSplit = new SentenceSplit(3, -1);
        assertEquals(-1, sentenceSplit.getSplitFlag());
    }
}