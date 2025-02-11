package pl.edu.agh.to2.lets_go_fishing.interline;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import pl.edu.agh.to2.lets_go_fishing.service.FileHandlerService;

import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import pl.edu.agh.to2.lets_go_fishing.utils.DialogUtils;

import java.io.File;
import java.io.IOException;

public class TranslationDialog {
    private final FileHandlerService fileHandlerService;

    public TranslationDialog(FileHandlerService fileHandlerService) {
        this.fileHandlerService = fileHandlerService;
    }

    public void showInterlinedTranslation(String interlinedTranslation) {
        int width = 600;
        int height = 400;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Interlined translation");
        alert.setHeaderText(null);
        TextArea textArea = new TextArea(interlinedTranslation);
        textArea.setWrapText(true);
        textArea.setEditable(false);
        textArea.setPrefSize(width, height);
        textArea.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12;");
        Button saveButton = new Button("Save interline to PDF/PNG");
        saveButton.setPrefSize(300, 30);
        saveButton.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
        HBox buttonBox = new HBox(saveButton);
        buttonBox.setAlignment(Pos.CENTER);
        Label label = new Label("");
        saveButton.setOnAction(event -> saveButtonClicked(interlinedTranslation));
        VBox vBox = new VBox(textArea, label, buttonBox);
        vBox.setPrefSize(width, height);
        alert.getDialogPane().setContent(vBox);
        alert.showAndWait();
    }

    public void saveButtonClicked(String interlinedTranslation) {
        File selectedFile = fileHandlerService.showSaveFileChooser("Save interlined translation",
                new FileChooser.ExtensionFilter("PDF files", "*.pdf"),
                new FileChooser.ExtensionFilter("PNG files", "*.png"));
        if (selectedFile != null) {
            try {
                fileHandlerService.saveToFile(selectedFile, interlinedTranslation);
                DialogUtils.showAlert("Success", "Translation in interline successfully saved!", Alert.AlertType.INFORMATION);
            } catch (IOException e) {
                e.printStackTrace();
                DialogUtils.showError("Failed to save file: " + selectedFile.getName());
            }
        }
    }
}
