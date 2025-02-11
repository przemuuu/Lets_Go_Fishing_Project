package pl.edu.agh.to2.lets_go_fishing.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseService {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseService.class);
    private final JdbcTemplate jdbcTemplate;
    private String language;

    @Autowired
    public DatabaseService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createLanguageTable(String language) {
        String tableName = sanitizeTableName(language);
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                "baseForm VARCHAR(255) PRIMARY KEY, " +
                "translation VARCHAR(255) NOT NULL)";
        jdbcTemplate.execute(sql);
    }

    public void saveFlashcardToTable(String baseForm, String translation, boolean confirmation) {
        String tableName = sanitizeTableName(this.language);

        if (!confirmation) {
            if (isFlashcardExisting(tableName, baseForm)) {
                throw new IllegalStateException("Flashcard already exists in table '" + tableName + "' for baseForm '" + baseForm + "'");
            } else {
                saveOrUpdateFlashcard(tableName, baseForm, translation);
            }
        } else {
            saveOrUpdateFlashcard(tableName, baseForm, translation);
        }
    }


    private boolean isFlashcardExisting(String tableName, String baseForm) {
        String checkSql = "SELECT COUNT(*) FROM " + tableName + " WHERE baseForm = ?";
        try {
            Integer count = jdbcTemplate.queryForObject(checkSql, Integer.class, baseForm);
            return count != null && count > 0;
        } catch (EmptyResultDataAccessException e) {
            logger.warn("No result found when checking if flashcard exists in table '{}' for baseForm '{}'", tableName, baseForm, e);
            return false;
        } catch (Exception e) {
            logger.error("Unexpected error while checking flashcard existence in table '{}' for baseForm '{}': {}", tableName, baseForm, e.getMessage(), e);
            return false;
        }
    }


    private void saveOrUpdateFlashcard(String tableName, String baseForm, String translation) {
        String sql = "MERGE INTO " + tableName + " (baseForm, translation) " +
                "KEY(baseForm) VALUES (?, ?)";
        jdbcTemplate.update(sql, baseForm, translation);
    }



    protected String sanitizeTableName(String language) {
        return language.replaceAll("\\W", "_").toLowerCase();
    }

    public List<String> getAllLanguageTables() {
        String sql = "SELECT table_name FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = 'PUBLIC'";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString("table_name"));
    }

    public void setLanguage(String language) {
        this.language = language;
    }


}