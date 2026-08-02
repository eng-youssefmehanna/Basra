package main.java.model;

import java.util.List;

public record Lobby(String gameCode, String hostUsername, List<String> players) {
}
