package pl.edu.agh.to2.lets_go_fishing.controller;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class FlashcardsControllerVisualsTest {
    @InjectMocks
    private FlashcardsController controller;

    @BeforeAll
    static void setup() {
        new JFXPanel();
    }

    @BeforeEach
    void setUp() {
        controller.progressToolBar = new ToolBar();
        controller.currentWordLabel = new Label();
        controller.submitWordButton = new Button();
        controller.saveFlashcardsButton = new Button();
        controller.yourTranslationLabel = new Label();
        controller.translatedTextField = new TextField();
        controller.languageChoiceBox = new ChoiceBox<>();
        controller.newLanguageTextField = new TextField();
        controller.translatedTextField = new TextField();
        controller.transcriptionTextField = new TextField();
        controller.partOfSpeechChoiceBox = new ChoiceBox<>();
        controller.alreadyBasicFormCheckBox = new CheckBox();
        controller.basicFormTextField  = new TextField();
        controller.otherPartOfSpeechTextField = new TextField();
        controller.partOfSentenceChoiceBox = new ChoiceBox<>();
    }

    @Test
    void initialize_shouldSetInitialVisibilityAndText() {
        Platform.runLater(() -> {
            assertFalse(controller.progressToolBar.isVisible());
            assertEquals("", controller.currentWordLabel.getText());
            assertFalse(controller.submitWordButton.isVisible());
            assertFalse(controller.yourTranslationLabel.isVisible());
            assertFalse(controller.translatedTextField.isVisible());
        });
    }

    @Test
    void resetVisuals_shouldUpdateVisibilityAndText() {
        Platform.runLater(() -> {
            controller.resetVisuals();

            assertFalse(controller.saveFlashcardsButton.isVisible());
            assertTrue(controller.translatedTextField.isVisible());
            assertTrue(controller.submitWordButton.isVisible());
            assertEquals("", controller.currentWordLabel.getText());
            assertTrue(controller.yourTranslationLabel.isVisible());
        });
    }

    @Test
    void resetVisuals_shouldResetAllFields() {
        Platform.runLater(() -> {
            controller.saveFlashcardsVBox.setVisible(true);
            controller.translationVBox.setVisible(false);
            controller.translatedTextField.setText("Sample text");

            controller.resetVisuals();

            assertFalse(controller.saveFlashcardsVBox.isVisible());
            assertTrue(controller.translationVBox.isVisible());
            assertEquals("", controller.translatedTextField.getText());
        });
    }

    @Test
    void onLanguageChosen_shouldToggleNewLanguageFieldVisibility() {
        controller.languageChoiceBox.getItems().addAll("English", "Other");
        controller.languageChoiceBox.setValue("Other");
        controller.onLanguageChosen();

        assertTrue(controller.newLanguageTextField.isVisible());

        controller.languageChoiceBox.setValue("English");
        controller.onLanguageChosen();

        assertFalse(controller.newLanguageTextField.isVisible());
    }

    @Test
    void VerifyTranslationData_AllFieldsValid() {
        controller.translatedTextField.setText("word");
        controller.partOfSpeechChoiceBox.setValue("noun");
        controller.alreadyBasicFormCheckBox.setSelected(true);
        controller.transcriptionTextField.setText("wɜːd");
        Platform.runLater(() -> {
            boolean result = controller.verifyTranslationData();
            assertTrue(result);
        });


    }

    @Test
    void VerifyTranslationData_MissingPartOfSpeech() {
        controller.translatedTextField.setText("word");
        controller.transcriptionTextField.setText("wɜːd");

        Platform.runLater(() -> {
            boolean result = controller.verifyTranslationData();

            assertFalse(result);
        });
    }

    @Test
    void OnAlreadyBasicFormChecked() {
        controller.alreadyBasicFormCheckBox.setSelected(true);
        controller.onAlreadyBasicFormChecked();
        assertTrue(controller.basicFormTextField.isDisabled());

        controller.alreadyBasicFormCheckBox.setSelected(false);
        controller.onAlreadyBasicFormChecked();
        assertFalse(controller.basicFormTextField.isDisabled());
    }

    @Test
    void OnPartOfSpeechChosen() {
        Platform.runLater(() -> {
        controller.partOfSpeechChoiceBox.setValue("Other");
        controller.onPartOfSpeechChosen();
        assertTrue(controller.otherPartOfSpeechTextField.isVisible());

        controller.partOfSpeechChoiceBox.setValue("noun");
        controller.onPartOfSpeechChosen();
        assertFalse(controller.otherPartOfSpeechTextField.isVisible());

        });

    }

    @Test
    void OnLanguageChosen() {
        controller.languageChoiceBox.setValue("Other");
        controller.onLanguageChosen();
        assertTrue(controller.newLanguageTextField.isVisible(), "Expected newLanguageTextField to be visible when languageChoiceBox is set to 'OTHER'.");

        controller.languageChoiceBox.setValue("English");
        controller.onLanguageChosen();
        assertFalse(controller.newLanguageTextField.isVisible(), "Expected newLanguageTextField to be hidden when languageChoiceBox is set to a value other than 'OTHER'.");
    }
}
