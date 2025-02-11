package pl.edu.agh.to2.lets_go_fishing.model;

public enum PartOfSpeech {
    NOUN("Noun"),
    PRONOUN("Pronoun"),
    VERB("Verb"),
    ADJECTIVE("Adjective"),
    ADVERB("Adverb"),
    PREPOSITION("Preposition"),
    CONJUNCTION("Conjunction"),
    INTERJECTION("Interjection"),
    OTHER("Other");

    private final String displayName;

    PartOfSpeech(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static PartOfSpeech fromString(String text) {
        for (PartOfSpeech partOfSpeech : PartOfSpeech.values()) {
            if (partOfSpeech.displayName.equalsIgnoreCase(text)) {
                return partOfSpeech;
            }
        }
        return null;
    }
}