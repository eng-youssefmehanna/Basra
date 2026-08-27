package main.java.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

// TODO number of games , number of wins, table.java

public class Player {
    private String name;
    private  List<Card> hand;
    private List<Card> capturedCards;
    private String userName;
    private String passwordHash;
    private String playerAvatar;
    private int wins;
    private int gamesPlayed;


// account constructor only for registration
    public Player(String name, String userName, String password) {
        this(name,userName);
        this.name = name;
        this.userName = userName;
        if(!isPasswordValid(password)) {
           throw new IllegalArgumentException("...");
        }
        this.passwordHash = hashPassword(password);

        playerAvatar= AvatarManager.getRandomAvatar();

    }


    // what game should know: username cause it appears at top in home screen
    public Player(String name, String userName){
        this.userName=userName;
        this.name=name;
        hand = new ArrayList<>();
        capturedCards = new ArrayList<>();
    }
// static factory pattern: create a player coming from the database , avoids double hashing
    public static Player fromDatabase(String name, String userName, String passwordHash, int wins
            , int gamesPlayed, String playerAvatar){
          Player player = new Player(name, userName);
           player.passwordHash=passwordHash;
           player.wins=wins;
           player.gamesPlayed=gamesPlayed;
           player.playerAvatar=playerAvatar;
           return player;
    }

        public static String hashPassword(String password) {
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
    public int getWins() {
        return wins;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }
    public String getUserName() {
        return userName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }


    public boolean isHandEmpty(){
        return hand.isEmpty();
    }

    @Override
    public String toString(){
        return String.format("Player: %s%n", name);
    }


    private boolean isPasswordValid(String password){

        return isPassSpecial(password) && (password.length()>=6);
    }

    private boolean isPassSpecial(String password){
        for(int i=0; i<password.length(); i++){
            char c= password.charAt(i);
            if(!Character.isLetterOrDigit(c)){
                return true;
            }
        }
        return false;
    }

    public String getPlayerAvatar() {
        return playerAvatar;
    }
}
