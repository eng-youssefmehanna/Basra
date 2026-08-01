package main.java.config;

import main.java.model.Player;
//singelton pattern
public class SessionManager {
    private static Player currentPlayer;

    public static Player getCurrentPlayer() { return currentPlayer; }
    public static void setCurrentPlayer(Player player) { currentPlayer = player; }
}