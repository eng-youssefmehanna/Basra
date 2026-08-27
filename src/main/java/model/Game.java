package main.java.model;

import java.util.*;
import java.util.stream.Collectors;

public class Game {
   private Deck deck;
   private List<Player> players;
   private List<Card> floor;
   private static final int drawingCount=4;

    public Game(){
        deck= new Deck();
        players=new ArrayList<>();
        floor=new ArrayList<>();
    }

    public int getDeckSize() { return deck.size(); }

    public void addPlayers(Player...player){
        // look for current size + sixe after addition
        if(players.size()+ player.length >4){
            throw new IllegalArgumentException("Max 4 players allowed");
        }
        players.addAll(List.of(player));
    }
    // 4 cards
    private void addToFloor( ){

        floor.addAll(deck.withdraw(drawingCount));
    }

    // overloading , 1 card when a player plays it
    void addToFloor(Card card){

        floor.add(card);
    }

    public List<Card> getFloor() {
        return floor;
    }

    public List<Player> getPlayers() {
        return players;
    }

    void dealToPlayers(){
        for(Player p : players){
            if(p.isHandEmpty()){
                p.addHandCards(deck.withdraw(drawingCount));
            }
        }
    }

    public TreeMap<Player, Integer> calculateScore(){
        return players.stream()
                .collect(Collectors.toMap(
                        player -> player,
                        player -> player.getCapturedCards()
                                .stream()
                                .mapToInt(Card::getCardVal)
                                .sum(),
                        (e1, e2) -> e1,
                        ()-> new TreeMap<>(Comparator.comparingInt( (Player p)->p.getCapturedCards()
                                        .stream()
                                        .mapToInt(Card::getCardVal)
                                        .sum()).thenComparing(Player::getName))
                ));
    }

    public void removeFromFloor(List<Card> cards){

        floor.removeAll(cards);
    }

    public boolean isCycleOver(){
        for(Player p: players){
            if(!p.isHandEmpty()){
                return false;
            }
        }
     return true;
    }

    public boolean isGameOver(){
        return isCycleOver() && deck.isEmpty();
    }

    public void start(){
        deck.shuffle();
        addToFloor();
        dealToPlayers();
    }
}
