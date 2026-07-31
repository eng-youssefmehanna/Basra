package main.java.model;

public enum Ranks {
    ACE, TWO, THREE, FOUR,
    FIVE, SIX, SEVEN, EIGHT
    , NINE, TEN, JACK, QUEEN,
    KING;

    public int getRankValue(){
        return switch(this){
            case  TWO-> 2;
            case THREE-> 3;
            case FOUR-> 4;
            case FIVE-> 5;
            case SIX-> 6;
            case SEVEN-> 7;
            case EIGHT-> 8;
            case NINE-> 9;
            case TEN-> 10;
            default->0;
        };
    }

}
