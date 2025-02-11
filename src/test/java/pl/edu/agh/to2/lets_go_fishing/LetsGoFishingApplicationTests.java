package pl.edu.agh.to2.lets_go_fishing;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.agh.to2.lets_go_fishing.controller.FlashcardsController;
import pl.edu.agh.to2.lets_go_fishing.model.FlashcardsModel;
import pl.edu.agh.to2.lets_go_fishing.service.FileHandlerService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
class LetsGoFishingApplicationTests {

	@Test
	void contextLoads() {
		//Application test
	}
	@Autowired
	private FlashcardsController flashcardsController;

	@Autowired
	private FileHandlerService fileHandlerService;

	@BeforeAll
	static void setUp(){
		new JFXPanel();
	}

	@Test
	void shouldGenerateFlashcardsFromTxtFile() throws IOException {
		File tempFile = Files.createTempFile("test", ".txt").toFile();
		Files.writeString(tempFile.toPath(), "Test\ndziewczynka\nma\nkota");

		String fileContent = fileHandlerService.readFile(tempFile);
		assertThat(fileContent).isEqualTo("Test\ndziewczynka\nma\nkota");

		FlashcardsModel model = new FlashcardsModel();
		model.setInputText(fileContent);
		model.parseInputText();

		List<String> words = new ArrayList<>();
		for(int i = 0; i < model.getTotalWords(); i++){
			words.add(model.getCurrentWord());
			model.nextWord();
		}

		Set<String> expectedWords = Set.of("Test", "dziewczynka", "ma", "kota");
		Set<String> actualWords = new HashSet<>(words);
		assertEquals(expectedWords, actualWords);

		FlashcardsModel model2 = new FlashcardsModel();
		model2.setInputText(fileContent);
		model2.parseInputText();

		model2.addTranslation("Test, Test");
		model2.addTranslation("dziewczynka, girl");
		model2.addTranslation("ma, has");
		model2.addTranslation("kota, cat");

		Platform.runLater(() -> {
			try {
				File output = Files.createTempFile("flashcards", ".csv").toFile();
				flashcardsController.onSaveFlashcardsClicked();

				List<String> savedContent = Files.readAllLines(output.toPath());
				assertEquals(List.of("Test, Test", "dziewczynka, girl", "ma, has", "kota, cat"), savedContent);

				tempFile.delete();
				output.delete();
			} catch (IOException e) {
				fail("Test failed due to exception: " + e.getMessage());
			}
		});
	}
}