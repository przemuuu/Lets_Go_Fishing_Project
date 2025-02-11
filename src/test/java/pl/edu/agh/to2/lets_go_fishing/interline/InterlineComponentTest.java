package pl.edu.agh.to2.lets_go_fishing.interline;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class InterlineComponentTest {
    private InterlineComponent interlineComponent;

    @BeforeEach
    void setUp() {
        interlineComponent = new InterlineComponent();
    }

    @Test
    void initialState_ShouldHaveEmptyLists() {
        assertThat(interlineComponent.getTranslatedWords()).isEmpty();
        assertThat(interlineComponent.getTranslations()).isEmpty();
        assertThat(interlineComponent.getTranscriptions()).isEmpty();
    }
    @Test
    void addWord() {
        interlineComponent.addWord("dog", "pies", "[dɔɡ]");

        assertThat(interlineComponent.getTranslatedWords()).containsExactly("dog");
        assertThat(interlineComponent.getTranslations()).containsExactly("pies");
        assertThat(interlineComponent.getTranscriptions()).containsExactly("[dɔɡ]");
    }

    @Test
    void getters_ShouldReturnMutableLists() {
        interlineComponent.addWord("initial", "initial", "initial");

        List<String> translatedWords = interlineComponent.getTranslatedWords();
        translatedWords.add("modified");

        List<String> translations = interlineComponent.getTranslations();
        translations.add("modified");

        List<String> transcriptions = interlineComponent.getTranscriptions();
        transcriptions.add("modified");

        assertThat(interlineComponent.getTranslatedWords()).containsExactly("initial", "modified");
        assertThat(interlineComponent.getTranslations()).containsExactly("initial", "modified");
        assertThat(interlineComponent.getTranscriptions()).containsExactly("initial", "modified");
    }

    @Test
    void clear() {
        interlineComponent.addWord("test", "test", "test");

        interlineComponent.clear();

        assertThat(interlineComponent.getTranslatedWords()).isEmpty();
        assertThat(interlineComponent.getTranslations()).isEmpty();
        assertThat(interlineComponent.getTranscriptions()).isEmpty();
    }
}