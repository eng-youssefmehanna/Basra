package main.java.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.config.SessionManager;
import main.java.model.Lobby;
import main.java.repository.Lobbyrepository;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Lobbyviewmodel {
    private StringProperty gameCode = new SimpleStringProperty();
    private ObservableList<String> players;
    Lobbyrepository LobbyRepo;

    public Lobbyviewmodel(Lobbyrepository LobbyRepo) throws IOException, InterruptedException {
        this.LobbyRepo=LobbyRepo;
        String hostUsername = SessionManager.getCurrentPlayer().getUserName();
        String code = generateCode();

        Lobby lobby = new Lobby(code, hostUsername, List.of(hostUsername));
        LobbyRepo.registerLobby(lobby);

        gameCode.set("Game code: "+code);
        players = FXCollections.observableArrayList(hostUsername);
    }

    public Lobbyviewmodel(Lobbyrepository lobbyRepo, String code) throws IOException, InterruptedException {
        this.LobbyRepo = lobbyRepo;
        Lobby lobby = LobbyRepo.fetchLobby(code);
        gameCode.set("Game code: " + code);
        players = FXCollections.observableArrayList(lobby.players());
    }

    public StringProperty gameCodeProperty(){
        return gameCode;
    }

    public ObservableList<String> getPlayers() {
        return players;
    }



    // TODOfixed)fix code crashes when mulriple users genratecode

    private static String generateCode(){
        char[] code= new char[5];
        ThreadLocalRandom random = ThreadLocalRandom.current();

        code[0]= (char) (48+ random.nextInt(10));
        code[1]= (char) (48+ random.nextInt(10));

        code[2]= (char) (65+ random.nextInt(26));
        code[3]= (char) (65+ random.nextInt(26));

        char[] specials={'!','@','#','*','%','?'};
        code[4]=specials[random.nextInt(specials.length)];

        shuffleArray(code, random);
               return new String(code);
    }

    private static void shuffleArray(char[] chars, ThreadLocalRandom random){
        for(int i=chars.length-1; i>0; i--){
            int index= random.nextInt(i+1);
            char temp = chars[index];
            chars[index]=chars[i];
            chars[i]=temp;
        }
    }

}