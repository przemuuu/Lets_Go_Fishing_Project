package pl.edu.agh.to2.lets_go_fishing.model;

import java.util.Arrays;

public enum PartOfSentence {
    SUBJECT("Subject"), // what or who is doing the action - podmiot
    PREDICATE("Predicate"), // what the subject is doing - orzeczenie
    DIRECT_OBJECT("Direct object"), // what the subject is doing the action to - dopełnienie bezpośrednie
    INDIRECT_OBJECT("Indirect object"), // to whom or for whom the action is done - dopełnienie pośrednie
    SUBJECT_COMPLEMENT("Subject complement"), // renames the subject - informacja o podmiocie (po "to be" i podobnych)
    OBJECT_COMPLEMENT("Object complement"), // renames the object - uzupełnienie dopełnienia
    ADVERBIAL("Adverbial"), // determines the time, place, manner, cause - okolicznik
    MODIFIER("Modifier"), // adds information to another part of the sentence - modyfikator (np. przymiotnik, przysłówek)
    DETERMINER("Determiner"), // introduces a noun - określenie (np. "the", "a", "this")
    APPOSITION("Apposition"), // renames the noun - wyrażenie równoważne (np. "my friend, the doctor") (np. "She likes tea and coffee.")
    COORDINATOR("Coordinator"), // connects words, phrases, or clauses of equal importance - spójnik współrzędny
    SUBORDINATOR("Subordinator"), // connects a dependent clause to an independent clause - spójnik podrzędny (np. "I stayed home because it rained.")
    CORRELATIVE("Correlative"), // connects words, phrases, or clauses of equal importance - spójnik korelacyjny (np. "Either you go, or I will.")
    PREPOSITION("Preposition"), // shows the relationship between a noun and another word - przyimek (np. "in", "on", "at")
    EXCLAMATION("Exclamation"), // expresses strong emotion - wykrzyknik
    OTHER("Other");
    private final String displayName;

    PartOfSentence(String displayName) { this.displayName = displayName; }

    public String getDisplayName() {
        return displayName;
    }

    public static PartOfSentence fromString(String text) {
        return Arrays.stream(PartOfSentence.values())
                .filter(part -> part.displayName.equalsIgnoreCase(text))
                .findFirst()
                .orElse(null);
    }
}

// https://www.occc.edu/wp-content/legacy/writingcenter/Parts%20of%20a%20Sentence.pdf