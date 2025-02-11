package pl.edu.agh.to2.lets_go_fishing.service;

import org.springframework.stereotype.Service;
import pl.edu.agh.to2.lets_go_fishing.model.FlashcardsModel;
import pl.edu.agh.to2.lets_go_fishing.utils.TranslationFormatter;

@Service
public class FlashcardsService {

    private final FlashcardsModel model;

    public FlashcardsService() {
        this.model = new FlashcardsModel();
    }

    public void addTranslation(String word, String translation) {
        String formattedTranslation = TranslationFormatter.formatTranslation(word, translation);
        model.addTranslation(formattedTranslation);
    }

    public FlashcardsModel getModel() {
        return model;
    }
}