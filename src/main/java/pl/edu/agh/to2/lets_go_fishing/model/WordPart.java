package pl.edu.agh.to2.lets_go_fishing.model;

public class WordPart {
    private String word;
    private PartOfSentence partOfSentence;

    public WordPart(String word, PartOfSentence partOfSentence) {
        this.word = word;
        this.partOfSentence = partOfSentence;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public PartOfSentence getPartOfSentence() {
        return partOfSentence;
    }

    public void setPartOfSentence(PartOfSentence partOfSentence) {
        this.partOfSentence = partOfSentence;
    }
}