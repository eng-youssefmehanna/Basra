package main.java.viewmodel;

import main.java.config.SessionManager;
import main.java.model.state.JoinResultState;
import main.java.model.Lobby;
import main.java.model.Player;
import main.java.repository.Lobbyrepository;

import java.io.IOException;

public class Joinviewmodel {

    private static final int MAX_PLAYERS = 4;

    private final Lobbyrepository lobbyRepo;
    private final Player currentPlayer;

    public Joinviewmodel(Lobbyrepository lobbyRepo) {
        this.lobbyRepo = lobbyRepo;
        this.currentPlayer = SessionManager.getCurrentPlayer();
    }

    public JoinResultState tryJoin(String code) throws IOException, InterruptedException {
        Lobby lobby = lobbyRepo.fetchLobby(code);
        if (lobby == null){
            return JoinResultState.NOT_FOUND;}
        if (lobby.players().size() >= MAX_PLAYERS) {
            return JoinResultState.LOBBY_FULL;
        }
        lobbyRepo.updateLobby(code, currentPlayer.getUserName());
        return JoinResultState.SUCCESS;
    }
}