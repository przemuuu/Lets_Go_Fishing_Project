package pl.edu.agh.to2.lets_go_fishing.utils;

public class InputValidator {

    private InputValidator() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean isValidInput(String input) {
        return !input.isEmpty() && input.split("[^\\p{L}\\p{N}]+").length > 0;
    }
}
