package pl.edu.agh.to2.lets_go_fishing.controller;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.edu.agh.to2.lets_go_fishing.model.FlashcardsModel;
import pl.edu.agh.to2.lets_go_fishing.service.FileHandlerService;
import pl.edu.agh.to2.lets_go_fishing.service.FlashcardsService;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
class FlashcardsControllerTest {
    @Mock
    private FlashcardsModel flashcardsModel;

    @Mock
    private FileHandlerService service;

    @Mock
    private FlashcardsService flashcardsService;

    @InjectMocks
    private FlashcardsController controller;


    @BeforeAll
    static void setUp() {
        new JFXPanel();
    }

    @Test
    void onSelectFileClicked_shouldClearTextAreaOnIOException() {
        Platform.runLater(() -> {
            try {
                File mockFile = new File("test.txt");
                Mockito.when(service.readFile(mockFile)).thenThrow(new IOException());

                controller.onSelectFileClicked();

                assertTrue(controller.inputTextArea.getText().isEmpty());
            } catch (IOException e) {
                fail("Test failed due to exception: " + e.getMessage());
            }
        });
    }

    @Test
    void onCreateFlashcardsClicked_shouldParseInputAndShowProgress() {
        Platform.runLater(() -> {
            controller.inputTextArea.setText("Test1\nTest2\nTest3");

            controller.onCreateFlashcardsClicked();

            Mockito.verify(flashcardsModel).setInputText("Test1\nTest2\nTest3");
            Mockito.verify(flashcardsModel).parseInputText();

            assertTrue(controller.progressToolBar.isVisible());
            assertEquals("Test1", controller.currentWordLabel.getText());
        });
    }

    @Test
    void onSubmitWordClicked_shouldUpdateProgressAndHandleLastWord() {
        Platform.runLater(() -> {
            Mockito.when(flashcardsModel.getCurrentWord()).thenReturn("Word1", "Word2", null);
            controller.translatedTextField.setText("Translation1");

            controller.onSubmitWordClicked();


            Mockito.verify(flashcardsModel).addTranslation("Word1, Translation1");
            Mockito.verify(flashcardsModel).nextWord();


            assertFalse(controller.translatedTextField.isVisible());
            assertFalse(controller.submitWordButton.isVisible());
            assertTrue(controller.saveFlashcardsButton.isVisible());
        });
    }


    @Test
    void updateCurrentWord() {
        Platform.runLater(() -> {
            Mockito.when(flashcardsService.getModel()).thenReturn(flashcardsModel);
            Mockito.when(flashcardsModel.getCurrentWord()).thenReturn("Word1");

            controller.updateCurrentWord();

            assertEquals("Word1", controller.currentWordLabel.getText());
        });
    }

    @Test
    void updateCurrentWord_NoCurrentWord() {
        Platform.runLater(() -> {
            Mockito.when(flashcardsService.getModel()).thenReturn(flashcardsModel);
            Mockito.when(flashcardsModel.getCurrentWord()).thenReturn(null);

            controller.updateCurrentWord();

            assertEquals("All done! :)", controller.currentWordLabel.getText());
        });
    }


}