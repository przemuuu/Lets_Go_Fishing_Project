package pl.edu.agh.to2.lets_go_fishing.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class SentencePartMapper {
    private static final Map<PartOfSpeech, List<PartOfSentence>> mapping = new EnumMap<>(PartOfSpeech.class);

    private SentencePartMapper() {
    }

    static {
        mapping.put(PartOfSpeech.NOUN, Arrays.asList(
                PartOfSentence.SUBJECT,
                PartOfSentence.DIRECT_OBJECT,
                PartOfSentence.INDIRECT_OBJECT,
                PartOfSentence.SUBJECT_COMPLEMENT,
                PartOfSentence.OBJECT_COMPLEMENT,
                PartOfSentence.APPOSITION,
                PartOfSentence.DETERMINER
        ));
        mapping.put(PartOfSpeech.PRONOUN, Arrays.asList(
                PartOfSentence.SUBJECT,
                PartOfSentence.DIRECT_OBJECT,
                PartOfSentence.INDIRECT_OBJECT,
                PartOfSentence.SUBJECT_COMPLEMENT,
                PartOfSentence.MODIFIER
        ));
        mapping.put(PartOfSpeech.VERB, Collections.singletonList(PartOfSentence.PREDICATE));
        mapping.put(PartOfSpeech.ADJECTIVE, Arrays.asList(
                PartOfSentence.MODIFIER,
                PartOfSentence.SUBJECT_COMPLEMENT,
                PartOfSentence.OBJECT_COMPLEMENT
        ));
        mapping.put(PartOfSpeech.ADVERB, Collections.singletonList(PartOfSentence.MODIFIER));
        mapping.put(PartOfSpeech.PREPOSITION, Collections.singletonList(PartOfSentence.PREPOSITION));
        mapping.put(PartOfSpeech.CONJUNCTION, Arrays.asList(
                PartOfSentence.COORDINATOR,
                PartOfSentence.SUBORDINATOR,
                PartOfSentence.CORRELATIVE
        ));
        mapping.put(PartOfSpeech.INTERJECTION, Collections.singletonList(PartOfSentence.EXCLAMATION));
        mapping.put(PartOfSpeech.OTHER, Arrays.asList(
                PartOfSentence.SUBJECT,
                PartOfSentence.PREDICATE,
                PartOfSentence.DIRECT_OBJECT,
                PartOfSentence.INDIRECT_OBJECT,
                PartOfSentence.SUBJECT_COMPLEMENT,
                PartOfSentence.OBJECT_COMPLEMENT,
                PartOfSentence.ADVERBIAL,
                PartOfSentence.MODIFIER,
                PartOfSentence.DETERMINER,
                PartOfSentence.COORDINATOR,
                PartOfSentence.SUBORDINATOR,
                PartOfSentence.CORRELATIVE,
                PartOfSentence.PREPOSITION,
                PartOfSentence.EXCLAMATION,
                PartOfSentence.OTHER
        ));
    }

    public static List<PartOfSentence> getPossibleParts(PartOfSpeech partOfSpeech) {
        return mapping.getOrDefault(partOfSpeech, Collections.singletonList(PartOfSentence.OTHER));
    }
}
