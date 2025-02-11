package pl.edu.agh.to2.lets_go_fishing.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class DialogUtils {

    private DialogUtils(){
        throw new IllegalStateException("Utility class");
    }

    public static void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static ButtonType showConfirmation(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
        return alert.getResult();
    }

    public static void showError(String message) {
        showAlert("Error", message, Alert.AlertType.ERROR);
    }
}