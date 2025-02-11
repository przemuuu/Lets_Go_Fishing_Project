package pl.edu.agh.to2.lets_go_fishing.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FlashcardsRepositoryTest {

    private FlashcardsRepository flashcardsRepository;

    @BeforeEach
    void setUp() {
        flashcardsRepository = Mockito.mock(FlashcardsRepository.class);
    }

    @Test
    void addFlashcard() {
        Flashcard flashcard = Mockito.mock(Flashcard.class);
        doNothing().when(flashcardsRepository).addFlashcard(flashcard);

        flashcardsRepository.addFlashcard(flashcard);

        verify(flashcardsRepository, times(1)).addFlashcard(flashcard);
    }

    @Test
    void getAllFlashcards() {
        Flashcard flashcard1 = Mockito.mock(Flashcard.class);
        Flashcard flashcard2 = Mockito.mock(Flashcard.class);
        when(flashcardsRepository.getAllFlashcards()).thenReturn(Collections.unmodifiableList(List.of(flashcard1, flashcard2)));

        List<Flashcard> flashcards = flashcardsRepository.getAllFlashcards();

        assertEquals(flashcards, List.of(flashcard1, flashcard2));
        verify(flashcardsRepository, times(1)).getAllFlashcards();
    }

    @Test
    void clear() {
        flashcardsRepository.addFlashcard(Mockito.mock(Flashcard.class));
        doNothing().when(flashcardsRepository).clear();

        flashcardsRepository.clear();

        assertTrue(flashcardsRepository.getAllFlashcards().isEmpty());
        verify(flashcardsRepository, times(1)).clear();
    }
}