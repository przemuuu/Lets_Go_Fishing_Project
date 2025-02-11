package pl.edu.agh.to2.lets_go_fishing.utils;

public class TranslationFormatter {

    private TranslationFormatter() {
        throw new IllegalStateException("Utility class");
    }

    public static String formatTranslation(String word, String translation) {
        return word + ", " + translation;
    }
}