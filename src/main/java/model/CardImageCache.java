package main.java.model;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class CardImageCache {
    private static final String BACK_URL = "https://deckofcardsapi.com/static/img/back.png";
    private static final Map<String, Image> cache = new HashMap<>();

    public static Image getImage(Card card) {
        String url = card.getImageUrl();
        if (!cache.containsKey(url)) {
            cache.put(url, new Image(url, true));
        }
        return cache.get(url);
    }

    public static void preloadAll(List<Card> deck) {
        cache.put(BACK_URL, new Image(BACK_URL, true)); // once
        for (Card card : deck) {
            cache.put(card.getImageUrl(), new Image(card.getImageUrl(), true));
        }
    }

    public static Image getBackImage() {
        if (!cache.containsKey(BACK_URL)) {
            cache.put(BACK_URL, new Image(BACK_URL, true));
        }
        return cache.get(BACK_URL);
    }
}
