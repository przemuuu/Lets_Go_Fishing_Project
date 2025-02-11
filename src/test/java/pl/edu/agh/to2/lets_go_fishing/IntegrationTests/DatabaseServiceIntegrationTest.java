package pl.edu.agh.to2.lets_go_fishing.IntegrationTests;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import pl.edu.agh.to2.lets_go_fishing.service.DatabaseService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
@SpringJUnitConfig(DatabaseService.class)
class DatabaseServiceIntegrationTest {

    @Autowired
    private DatabaseService databaseService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeAll
    static void setFX() {
        new JFXPanel();
    }

    @BeforeEach
    void setUp() {
        databaseService.setLanguage("TestLanguage");
        databaseService.createLanguageTable("TestLanguage");
    }

    @Test
    void createLanguageTableAndVerify() {
        databaseService.createLanguageTable("Polish");
        List<String> tables = databaseService.getAllLanguageTables();
        assertTrue(tables.contains("POLISH"));
    }

    @Test
    void saveFlashcardToTable() {
        databaseService.saveFlashcardToTable("cat", "kot", false);

        String sql = "SELECT translation FROM testlanguage WHERE baseForm = ?";
        String translation = jdbcTemplate.queryForObject(sql, String.class, "cat");
        assertEquals("kot", translation);
    }

    @Test
    void saveDuplicateFlashcard() {
        Platform.runLater(() -> {
            databaseService.saveFlashcardToTable("dog", "pies", false);

            String sql = "SELECT translation FROM testlanguage WHERE baseForm = ?";
            String translation = jdbcTemplate.queryForObject(sql, String.class, "dog");
            assertEquals("pies", translation);


            databaseService.saveFlashcardToTable("dog", "canine", true);
            translation = jdbcTemplate.queryForObject(sql, String.class, "dog");
            assertEquals("canine", translation);
        });
    }

    @Test
    void getAllLanguageTables() {
        databaseService.createLanguageTable("polish");
        databaseService.createLanguageTable("french");

        List<String> tables = databaseService.getAllLanguageTables();
        assertTrue(tables.contains("TESTLANGUAGE"));
        assertTrue(tables.contains("POLISH"));
        assertTrue(tables.contains("FRENCH"));
    }
}