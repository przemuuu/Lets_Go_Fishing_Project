package pl.edu.agh.to2.lets_go_fishing.model;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class FlashcardsRepository {
    private final List<Flashcard> flashcards = new ArrayList<>();

    public void addFlashcard(Flashcard flashcard) {
        flashcards.add(flashcard);
    }

    public List<Flashcard> getAllFlashcards() {
        return Collections.unmodifiableList(flashcards);
    }

    public void clear() {
        flashcards.clear();
    }
}
