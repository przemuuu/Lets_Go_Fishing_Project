package pl.edu.agh.to2.lets_go_fishing;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.edu.agh.to2.lets_go_fishing.gui.FlashcardsApp;

@SpringBootApplication
public class LetsGoFishingApplication {
	public static void main(String[] args) {
		Application.launch(FlashcardsApp.class);
	}
}
