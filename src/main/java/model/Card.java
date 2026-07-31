package main.java.model;

import java.util.Objects;

public class Card {
    Suits suit;
    Ranks rank;

    public Card(Suits suit, Ranks rank) {
        this.suit = suit;
        this.rank = rank;
    }

    @Override
    public String toString() {
        return String.format("%s of %s%n", rank, suit);
    }

    public boolean isJack() {
        return rank == Ranks.JACK;
    }

    public boolean isSevenDiamonds() {
        return rank == Ranks.SEVEN && suit == Suits.DIAMONDS;
    }

    public boolean isACE() {
        return rank == Ranks.ACE;
    }

    public boolean isTwoOfClubs() {
        return rank == Ranks.TWO && suit == Suits.CLUBS;
    }

    public int getCardVal() {
        if (this.isJack() || this.isACE()) {
            return 1;
        } else if (this.isSevenDiamonds()) {
            return 10;
        } else if (this.isTwoOfClubs()) {
            return 2;
        }
        return 1;
    }

    public Ranks getRank() {
        return rank;
    }

    public Suits getSuits() {
        return suit;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Card c)) return false;
        return rank == c.rank && suit == c.suit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank, suit);
    }


}
