package main.java.repository;

import main.java.model.Player;

import java.io.IOException;

public interface IUserRepository {
    boolean registerUser(Player player) throws IOException, InterruptedException;
    Player fetchUser(String username, String passwordHash) throws IOException, InterruptedException;
    boolean updateStats(String username, int wins, int gamesPlayed) throws IOException, InterruptedException;
}
