package pl.edu.agh.to2.lets_go_fishing.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SentenceParser {
    private static final String SENTENCE_REGEX = "[^.!?]+";
    private static final String WORD_REGEX = "\\p{L}[\\p{L}\\p{M}\\p{Pd}]*|\\p{N}+";
    public List<Integer> parse(String inputText) {
        Pattern sentencePattern = Pattern.compile(SENTENCE_REGEX);
        Matcher sentenceMatcher = sentencePattern.matcher(inputText);

        List<Integer> wordCounts = new ArrayList<>();
        Pattern wordPattern = Pattern.compile(WORD_REGEX);

        while (sentenceMatcher.find()) {
            String sentence = sentenceMatcher.group();
            Matcher wordMatcher = wordPattern.matcher(sentence);
            int wordCount = 0;
            while (wordMatcher.find()) {
                wordCount++;
            }
            wordCounts.add(wordCount);
        }
        return wordCounts;
    }
}
