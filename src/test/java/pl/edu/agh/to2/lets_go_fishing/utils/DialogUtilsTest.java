package pl.edu.agh.to2.lets_go_fishing.utils;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.verify;

class DialogUtilsTest {

    @BeforeAll
    static void setUp() {
        new JFXPanel();
    }

    @Test
    void showAlert() {
        Alert alertMock = Mockito.mock(Alert.class);
        Alert.AlertType alertType = Alert.AlertType.INFORMATION;

        Platform.runLater(() -> {
            DialogUtils.showAlert("Test title", "Test message", alertType);

            verify(alertMock, Mockito.times(1)).setTitle("Test title");
        });
    }

    @Test
    void showConfirmation() {
        Alert alertMock = Mockito.mock(Alert.class);
        Mockito.when(alertMock.showAndWait()).thenReturn(java.util.Optional.of(ButtonType.OK));

        Platform.runLater(() -> {
            ButtonType result = DialogUtils.showConfirmation("Confirmation message");

            verify(alertMock, Mockito.times(1)).setTitle("Confirmation");
            verify(alertMock, Mockito.times(1)).setContentText("Confirmation message");
            verify(alertMock, Mockito.times(1)).setAlertType(Alert.AlertType.CONFIRMATION);
            verify(alertMock, Mockito.times(1)).showAndWait();

            assertEquals(ButtonType.OK, result);
        });
    }
}