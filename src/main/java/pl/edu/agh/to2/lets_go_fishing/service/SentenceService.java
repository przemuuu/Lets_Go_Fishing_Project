package pl.edu.agh.to2.lets_go_fishing.service;

import org.springframework.stereotype.Service;
import pl.edu.agh.to2.lets_go_fishing.model.PartOfSentence;
import pl.edu.agh.to2.lets_go_fishing.model.SentenceModel;

@Service
public class SentenceService {
    private final SentenceModel model;

    public SentenceService() {
        this.model = new SentenceModel();
    }

    public SentenceModel getModel() {
        return model;
    }

    public void addWordToSentence(String word, PartOfSentence partOfSentence) {
        model.addWordToSentence(word, partOfSentence);
    }
}