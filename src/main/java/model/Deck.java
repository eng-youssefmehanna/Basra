package main.java.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
   private List<Card> deck;

    public Deck(){
        deck= new ArrayList<>();
        populateDeck();
    }

    private void populateDeck(){
        for(Suits suit : Suits.values()) {
            for(Ranks rank : Ranks.values()) {
                deck.add(new Card(suit,rank));
            }
        }
    }

    public void shuffle(){
        Collections.shuffle(deck);
    }

    public List<Card> withdraw(int numOfCards){
        List<Card> removedCards= new ArrayList<>();
        if(numOfCards > deck.size()) {
            throw new IllegalArgumentException("Not enough cards in deck");}

        for(int i = 0; i < numOfCards; i++) {
                removedCards.add(deck.remove(deck.size() - 1)); // removing for end is O(1) cause from
            }

        return removedCards;
    }

    public boolean isEmpty(){
        return deck.isEmpty();
    }

    public int size() { return deck.size(); }


}
