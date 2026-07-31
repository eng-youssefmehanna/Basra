package main.java.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

import static java.util.Collections.addAll;


@FunctionalInterface
interface specialCardAction<Card>{
    List<Card> execute();
}

public class GamePlay {
    private Game game;
    private int indexOfCurrPlayer;


    public GamePlay(Game game) {
        this.game = game;
        indexOfCurrPlayer = 0;
    }

    private void nextTurn() {
        indexOfCurrPlayer++;
        indexOfCurrPlayer %= (game.getPlayers().size());
    }

    public Player getCurrPlayer() {
        return game.getPlayers().get(indexOfCurrPlayer);
    }

    private List<Card> jackAction(){
        specialCardAction<Card> cardAct= ()-> new ArrayList<>(game.getFloor());
        return cardAct.execute();
    }

    private List<Card> sevenOfDiamondsAction(){
        specialCardAction<Card> cardAct= ()-> new ArrayList<>(game.getFloor());
        return cardAct.execute();
    }

    private List<Card> aceAction(){
        Scanner input=new Scanner(System.in);
        System.out.println("choose card from floor  by index");
        printFloorCards();
       int choice= input.nextInt();
        return new ArrayList<Card>(List.of(game.getFloor().get(choice)));
    }

    private void printFloorCards(){
        int i = 0;
        List<Card> temp=new ArrayList<>(game.getFloor());
        for(Card c: temp){
            System.out.println("card num"+i+": "+c.toString());
            i++;
        }
    }

    public List<Card> returnCaptures(Card card) {
        List<Card> temp = new ArrayList<>();
        temp.addAll(game.getFloor());
        // case 1
       if(card.isJack()){ return jackAction();}
       if(card.isSevenDiamonds()){return sevenOfDiamondsAction();}
       if(card.isACE()){ return aceAction();}

           // case 2
            int floorSum = temp.stream().map(Card::getRank).mapToInt(Ranks::getRankValue).sum();
            if (floorSum == card.getRank().getRankValue()) {
                return new ArrayList<Card>(game.getFloor());
            }

        // case 3
        List<Card> subset = findBestSubset(temp, card.getRank().getRankValue());
        if (!subset.isEmpty()) return subset;

        //case 4
        for (Card c : temp) {
            if (card.equals(c)) {
                return List.of(c);
            }
        }
        return new ArrayList<Card>();
    }


    private List<Card> findBestSubset(List<Card> floor, int target) {
        List<Card> best = new ArrayList<>();
        findBestSubsetHelper(floor, 0, target, new ArrayList<>(), best);
        return best;
    }

    private void findBestSubsetHelper(List<Card> floor, int index, int remaining, List<Card> current, List<Card> best) {
        if (remaining == 0) {
            int currentVal = 0;
            int bestVal = 0;
            for (Card c : current) currentVal += c.getCardVal();
            for (Card c : best) bestVal += c.getCardVal();
            if (currentVal > bestVal) {
                best.clear();
                best.addAll(current);
            }
            return;
        }
        if (index == floor.size() || remaining < 0) return;

        current.add(floor.get(index));
        findBestSubsetHelper(floor, index + 1, remaining - floor.get(index).getRank().getRankValue(), current, best);
        current.remove(current.size() - 1);
        findBestSubsetHelper(floor, index + 1, remaining, current, best);
    }
    //orchestrates the flow of one turn
    public void playTurn(Card card,Player player){
        // step1
        if(game.isCycleOver()){
            game.dealToPlayers();
        }
        //step 2
        player.playCard(card);
        // step 3
        List<Card> captured = returnCaptures(card);
        if (!captured.isEmpty()) {
            player.addCapturedCards(captured);
            game.removeFromFloor(captured);
        } else {
            game.addToFloor(card);
        }

        //step 4
        nextTurn();

    }

    public TreeMap<Player, Integer> endGame() {
        if (!game.isGameOver()) return null;
        return game.calculateScore();
    }
}
