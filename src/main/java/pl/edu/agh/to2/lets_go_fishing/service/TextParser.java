package pl.edu.agh.to2.lets_go_fishing.service;

import javafx.util.Pair;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TextParser {
    private static final String REGEX = "\\p{L}[\\p{L}\\p{M}\\p{Pd}]*|\\p{N}+";
    public List<Pair<String,Boolean>> parse(String inputText) {
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(inputText);
        List<Pair<String, Boolean>> words = new ArrayList<>();
        Set<String> uniqueWords = new HashSet<>();
        while (matcher.find()) {
            if (!uniqueWords.add(matcher.group())) {
                words.add(new Pair<>(matcher.group(), true));
            } else {
                words.add(new Pair<>(matcher.group(), false));
            }
        }
        return words;
    }
}
