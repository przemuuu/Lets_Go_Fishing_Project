package pl.edu.agh.to2.lets_go_fishing.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.edu.agh.to2.lets_go_fishing.model.*;
import pl.edu.agh.to2.lets_go_fishing.service.DatabaseService;
import pl.edu.agh.to2.lets_go_fishing.service.FileHandlerService;
import pl.edu.agh.to2.lets_go_fishing.service.FlashcardsService;
import pl.edu.agh.to2.lets_go_fishing.service.InterlineService;
import pl.edu.agh.to2.lets_go_fishing.service.SentenceService;
import pl.edu.agh.to2.lets_go_fishing.utils.DialogUtils;
import pl.edu.agh.to2.lets_go_fishing.utils.FlashcardsSaver;
import pl.edu.agh.to2.lets_go_fishing.utils.InputValidator;
import pl.edu.agh.to2.lets_go_fishing.interline.TranslationDialog;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Controller
public class FlashcardsController {
    @FXML
    protected Button submitWordButton;
    @FXML
    protected Button saveFlashcardsButton;
    @FXML
    protected TextArea inputTextArea;
    @FXML
    protected TextField translatedTextField;
    @FXML
    protected TextField otherPartOfSpeechTextField;
    @FXML
    protected TextField basicFormTextField;
    @FXML
    protected TextField transcriptionTextField;
    @FXML
    protected TextField newLanguageTextField;
    @FXML
    protected TextField otherPartOfSentenceTextField;
    @FXML
    protected Label currentWordLabel;
    @FXML
    private Label progressLabel;
    @FXML
    protected Label yourTranslationLabel;
    @FXML
    protected ToolBar progressToolBar;
    @FXML
    private ProgressBar translationProgressBar;
    @FXML
    protected VBox translationVBox;
    @FXML
    protected VBox saveFlashcardsVBox;
    @FXML
    protected ChoiceBox<String> partOfSpeechChoiceBox;
    @FXML
    protected ChoiceBox<String> languageChoiceBox;
    @FXML
    protected ChoiceBox<String> partOfSentenceChoiceBox;
    @FXML
    protected CheckBox alreadyBasicFormCheckBox;


    private final FileHandlerService fileHandlerService;
    public final FlashcardsService flashcardsService;
    private final DatabaseService databaseService;
    private final InterlineService interlineService;
    private final TranslationDialog translationDialog;
    private final SentenceService sentenceService;


    private static final String OTHER = "Other";

    @Autowired
    public FlashcardsController(FileHandlerService fileHandlerService, FlashcardsService flashcardsService, DatabaseService databaseService, InterlineService interlineService, SentenceService sentenceService) {
        this.fileHandlerService = fileHandlerService;
        this.flashcardsService = flashcardsService;
        this.databaseService = databaseService;
        this.interlineService = interlineService;
        this.translationDialog = new TranslationDialog(fileHandlerService);
        this.sentenceService = sentenceService;
    }


    @FXML
    public void onSelectFileClicked() {
        File selectedFile = fileHandlerService.showFileChooser("Select File",
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("Word Documents", "*.docx"),
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        if (selectedFile != null) {
            try {
                String fileContent = fileHandlerService.readFile(selectedFile);
                inputTextArea.setText(fileContent);
            } catch (IOException e) {
                e.printStackTrace();
                DialogUtils.showError("Failed to upload file: " + selectedFile.getName());
                inputTextArea.clear();
            }
        }
    }

    @FXML
    public void onCreateFlashcardsClicked() {
        if (!flashcardsService.getModel().getTranslations().isEmpty()) {
            ButtonType userChoice = DialogUtils.showConfirmation("You have unsaved flashcards. Do you want to discard them?");
            if (userChoice == ButtonType.CANCEL) {
                return;
            }
        }
        if (!InputValidator.isValidInput(inputTextArea.getText())) {
            DialogUtils.showError("Input text area contains no valid words.");
            return;
        }
        if (languageChoiceBox.getValue() == null) {
            DialogUtils.showError("Language is not chosen.");
            return;
        }
        String selectedLanguage = languageChoiceBox.getValue();
        if (OTHER.equals(selectedLanguage)) {
            selectedLanguage = newLanguageTextField.getText().trim();
            if(selectedLanguage.isEmpty()) {
                DialogUtils.showError("Language name is empty.");
                return;
            }
            databaseService.createLanguageTable(selectedLanguage);
            languageChoiceBox.getItems().add(selectedLanguage);
        }
        interlineService.clear();
        databaseService.setLanguage(selectedLanguage);
        sentenceService.getModel().setInputText(inputTextArea.getText());
        sentenceService.getModel().parseInputText();

        List<Sentence> sentences = sentenceService.getModel().getAllSentences();
        for(Sentence sentence : sentences) {
            SentenceSplit sentenceSplit = analyzeSentenceStructure(sentence);
            interlineService.addSentenceSplit(sentenceSplit);
        }

        flashcardsService.getModel().setInputText(inputTextArea.getText());
        flashcardsService.getModel().parseInputText();

        resetVisuals();
        progressToolBar.setVisible(true);
        updateCurrentWord();
        updateProgress();
    }

    @FXML
    public void onSaveFlashcardsClicked() {
        File file = fileHandlerService.showSaveFileChooser("Save Flashcards", new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        if (file != null) {
            try {
                saveFlashcardsToCsv(file);
                DialogUtils.showAlert("Success", "Flashcards saved successfully.", Alert.AlertType.INFORMATION);
                flashcardsService.getModel().setInputText("");
            } catch (IOException e) {
                e.printStackTrace();
                DialogUtils.showError("Failed to save flashcards.");
            }
        }
    }

    private void saveFlashcardsToCsv(File file) throws IOException {
        FlashcardsSaver.saveToCsv(file, flashcardsService.getModel().getTranslations());
        progressToolBar.setVisible(false);
    }

    private void saveFlashcardsToTable(String basicForm, String translation, boolean overwrite) {
        try{
            databaseService.saveFlashcardToTable(basicForm, translation, overwrite);
        } catch (IllegalStateException e) {
            ButtonType result = DialogUtils.showConfirmation("The translation for '" + basicForm + "' already exists. Do you want to overwrite it?");
            databaseService.saveFlashcardToTable(basicForm, translation, result == ButtonType.OK);
        }
    }

    @FXML
    public void onSubmitWordClicked() {
        if (verifyTranslationData()) {
            String currentWord = flashcardsService.getModel().getCurrentWord();
            boolean currentWordDuplicate = flashcardsService.getModel().isCurrentWordDuplicate();

            if (currentWord != null) {
                String partOfSpeech = partOfSpeechChoiceBox.getValue();
                if (partOfSpeech != null && partOfSpeech.equals(OTHER)) {
                        partOfSpeech = otherPartOfSpeechTextField.getText();
                }
                String basicForm = alreadyBasicFormCheckBox.isSelected() ? currentWord : basicFormTextField.getText();
                String transcription = transcriptionTextField.getText();

                String partOfSentence = partOfSentenceChoiceBox.getValue();
                if(partOfSentence != null && partOfSentence.equals(OTHER)) {
                    partOfSentence = otherPartOfSentenceTextField.getText();
                }

                flashcardsService.getModel().addTranslation(translatedTextField.getText(), partOfSpeech, basicForm, transcription);
                saveFlashcardsToTable(basicForm, translatedTextField.getText(), currentWordDuplicate);
                interlineService.addWord(currentWord, translatedTextField.getText(), transcription);
                sentenceService.addWordToSentence(currentWord, PartOfSentence.fromString(partOfSentence));
                flashcardsService.getModel().nextWord();
                updateCurrentWord();
                updateProgress();
            }

            if (flashcardsService.getModel().getCurrentWord() == null) {
                saveFlashcardsVBox.setVisible(true);
                submitWordButton.setVisible(false);
                translationVBox.setVisible(false);
                translationDialog.showInterlinedTranslation(interlineService.getInterlinedTranslation());
            }

            clearTranslationFields();
        }
    }

    void updateCurrentWord() {
        String currentWord = flashcardsService.getModel().getCurrentWord();
        currentWordLabel.setText(Objects.requireNonNullElse(currentWord, "All done! :)"));
    }

    private void updateProgress() {
        int totalWords = flashcardsService.getModel().getTotalWords();
        int currentIndex = flashcardsService.getModel().getCurrentWordIndex();
        progressLabel.setText(currentIndex + " / " + totalWords + " - " + (currentIndex * 100 / totalWords) + "%");
        translationProgressBar.setProgress((double) currentIndex / totalWords);
    }

    @FXML
    public void onAlreadyBasicFormChecked() {
        basicFormTextField.setDisable(alreadyBasicFormCheckBox.isSelected());
    }

    @FXML
    public void onPartOfSpeechChosen() {
        String partOfSpeech = partOfSpeechChoiceBox.getValue();
        if (partOfSpeech != null) {
            otherPartOfSpeechTextField.setVisible(partOfSpeech.equals(OTHER));
        }
        PartOfSpeech partOfSpeechEnum = PartOfSpeech.fromString(partOfSpeech);
        List<PartOfSentence> possiblePartsOfSentence = SentencePartMapper.getPossibleParts(partOfSpeechEnum);
        partOfSentenceChoiceBox.getItems().clear();
        otherPartOfSentenceTextField.setVisible(false);
        partOfSentenceChoiceBox.getItems().addAll(
                possiblePartsOfSentence.stream()
                        .map(PartOfSentence::getDisplayName)
                        .toList()
        );
        if(possiblePartsOfSentence.size() == 1) {
            partOfSentenceChoiceBox.setValue(possiblePartsOfSentence.get(0).getDisplayName());
        }
    }

    @FXML
    public void onPartOfSentenceChosen() {
        String partOfSentence = partOfSentenceChoiceBox.getValue();
        if(partOfSentence != null) {
            otherPartOfSentenceTextField.setVisible(partOfSentence.equals(OTHER));
        }
    }

    @FXML
    public void onLanguageChosen() {
        String selectedLanguage = languageChoiceBox.getValue();
        newLanguageTextField.setVisible(OTHER.equals(selectedLanguage));
    }

    public void initialize() {
        saveFlashcardsVBox.setVisible(false);
        currentWordLabel.setText("");
        submitWordButton.setVisible(false);
        progressToolBar.setVisible(false);
        translationVBox.setVisible(false);
        List<String> languageTables = databaseService.getAllLanguageTables();
        languageChoiceBox.getItems().clear();
        languageChoiceBox.getItems().addAll(languageTables);
        languageChoiceBox.getItems().add(OTHER);
        partOfSpeechChoiceBox.getItems().addAll(
                Arrays.stream(PartOfSpeech.values())
                        .map(PartOfSpeech::getDisplayName)
                        .toList()
        );
        partOfSentenceChoiceBox.getItems().addAll(
                Arrays.stream(PartOfSentence.values())
                        .map(PartOfSentence::getDisplayName)
                        .toList()
        );
        addEnterKeyHandler(translatedTextField);
        addEnterKeyHandler(otherPartOfSpeechTextField);
        addEnterKeyHandler(basicFormTextField);
        addEnterKeyHandler(transcriptionTextField);
    }

    protected void resetVisuals() {
        saveFlashcardsVBox.setVisible(false);
        currentWordLabel.setText("");
        submitWordButton.setVisible(true);
        translationVBox.setVisible(true);
        clearTranslationFields();
    }

    private void clearTranslationFields() {
        translatedTextField.clear();
        partOfSpeechChoiceBox.setValue(null);
        otherPartOfSpeechTextField.clear();
        basicFormTextField.clear();
        basicFormTextField.setDisable(false);
        alreadyBasicFormCheckBox.setSelected(false);
        transcriptionTextField.clear();
        partOfSentenceChoiceBox.setValue(null);
        otherPartOfSentenceTextField.clear();
    }

    protected boolean verifyTranslationData() {
        StringBuilder alertMessage = new StringBuilder();
        if(!translatedTextField.getText().isEmpty()) {
            if(partOfSpeechChoiceBox.getValue() == null) {
                alertMessage.append("Part of speech is not chosen! \n");
            } else if(partOfSpeechChoiceBox.getValue().equals(OTHER) && otherPartOfSpeechTextField.getText().isEmpty()) {
                alertMessage.append("Other part of speech is not typed! \n");
            }
            if(!alreadyBasicFormCheckBox.isSelected() && basicFormTextField.getText().isEmpty()) {
                alertMessage.append("Basic form is not typed! \n");
            }
            if(transcriptionTextField.getText().isEmpty()) {
                alertMessage.append("Transcription is not typed! \n");
            }
            if(partOfSentenceChoiceBox.getValue() == null) {
                alertMessage.append("Part of sentence is not chosen! \n");
            } else if(partOfSentenceChoiceBox.getValue().equals(OTHER) && otherPartOfSentenceTextField.getText().isEmpty()) {
                alertMessage.append("Other part of sentence is not typed! \n");
            }
        }
        if(alertMessage.isEmpty()) {
            return true;
        }
        DialogUtils.showAlert("Wrong data", alertMessage.toString(), Alert.AlertType.WARNING);
        return false;
    }

    private void addEnterKeyHandler(TextField textField) {
        textField.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                onSubmitWordClicked();
            }
        });
    }

    private SentenceSplit analyzeSentenceStructure(Sentence sentence) {
        int splitWordNumber = -1;
        int splitFlag = -1;
        List<WordPart> words = sentence.getWords();

        for (int i = 0; i < words.size(); i++) {
            WordPart word = words.get(i);
            PartOfSentence partOfSentence = word.getPartOfSentence();

            if (partOfSentence == PartOfSentence.SUBORDINATOR) {
                splitWordNumber = i;
                splitFlag = 1;
            } else if (partOfSentence == PartOfSentence.COORDINATOR) {
                splitWordNumber = i;
                splitFlag = 0;
            }
        }
        if(splitWordNumber == -1) {
            splitWordNumber = words.size();
            splitFlag = 0;
        }
        return new SentenceSplit(splitWordNumber, splitFlag);
    }
}