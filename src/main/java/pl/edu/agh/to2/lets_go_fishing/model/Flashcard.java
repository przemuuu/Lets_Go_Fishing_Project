package pl.edu.agh.to2.lets_go_fishing.model;

public class Flashcard {
    private final String word;
    private final String translation;
    private  String partOfSpeech;
    private  String baseForm;
    private  String transcription;

    public Flashcard(String word, String translation, String partOfSpeech, String baseForm, String transcription) {
        this.word = word;
        this.translation = translation;
        this.partOfSpeech = partOfSpeech;
        this.baseForm = baseForm;
        this.transcription = transcription;
    }

    public Flashcard(String word, String translation){
        this.word = word;
        this.translation = translation;
    }

    public String getWord() {
        return word;
    }

    public String getTranslation() {
        return translation;
    }

    public String getBaseForm() {
        return baseForm;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public String getTranscription() {
        return transcription;
    }

    @Override
    public String toString() {
        return word + ", " + translation + ", " + partOfSpeech + ", " + baseForm + ", " + transcription;
    }
}
