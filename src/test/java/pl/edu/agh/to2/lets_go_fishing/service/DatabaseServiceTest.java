package pl.edu.agh.to2.lets_go_fishing.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DatabaseServiceTest {

    private DatabaseService databaseService;

    @BeforeEach
    void setUp() {
        databaseService = new DatabaseService(null);
    }

    @Test
    void sanitizeTableName() {
        String result = databaseService.sanitizeTableName("English-Language");
        assertEquals("english_language", result);

        result = databaseService.sanitizeTableName("123Polish!");
        assertEquals("123polish_", result);

        result = databaseService.sanitizeTableName("Special@Chars&");
        assertEquals("special_chars_", result);
    }
}

