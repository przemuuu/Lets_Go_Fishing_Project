package pl.edu.agh.to2.lets_go_fishing.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pl.edu.agh.to2.lets_go_fishing.LetsGoFishingApplication;

import java.io.IOException;

public class FlashcardsApp extends Application {

    private ConfigurableApplicationContext applicationContext;
    private FXMLLoader fxmlLoader;

    @Override
    public void init() throws Exception {
        applicationContext = SpringApplication.run(LetsGoFishingApplication.class);
        fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(applicationContext::getBean);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        fxmlLoader.setLocation(getClass().getResource("/application.fxml"));
        primaryStage.setScene(new javafx.scene.Scene(fxmlLoader.load()));
        primaryStage.setTitle("Let's Go Fishing! - Flashcards");
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        applicationContext.close();
        Platform.exit();
    }

}
