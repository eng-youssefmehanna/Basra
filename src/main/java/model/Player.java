package main.java.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/* To do phase 3:
*  add password
* add username
* */

public class Player {
    private String name;
    private  List<Card> hand;
    private List<Card> capturedCards;
    private String userName;
    private String passwordHash;

// account constructor only for regstiration
    public Player(String name, String userName, String password) {
        this.name = name;
        this.userName = userName;
        this.passwordHash = hashPassword(password);
        hand = new ArrayList<>();
        capturedCards = new ArrayList<>();

    }
       // what game should know
    public Player(String name){
        this.name=name;
        hand = new ArrayList<>();
        capturedCards = new ArrayList<>();
    }

        private String hashPassword(String password) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] hash = md.digest(password.getBytes());
                return Base64.getEncoder().encodeToString(hash);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("SHA-256 not available", e);
            }
        }


    public Card playCard(Card card){
        hand.remove(card);
        return card;
    }

    public void addHandCards(List<Card> card){
        hand.addAll(card);
    }

    public void addCapturedCards(List<Card> card){
        capturedCards.addAll(card);
    }

    public List<Card> getCapturedCards(){
        return capturedCards;
    }

    public List<Card> getHand(){
        return hand;
    }

    public String getName(){
        return name;
    }

    public boolean isHandEmpty(){
        return hand.isEmpty();
    }

    @Override
    public String toString(){
        return String.format("Player: %s%n", name);
    }
}
