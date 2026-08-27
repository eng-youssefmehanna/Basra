package main.java.view;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.model.state.JoinResultState;
import main.java.repository.Lobbyrepository;
import main.java.viewmodel.Joinviewmodel;

import java.io.IOException;

public class Joincontroller {

    private Joinviewmodel viewmodel= new Joinviewmodel(new Lobbyrepository());

    @FXML
    TextField Code_enter;

    @FXML
    Label JoinLabel;

    @FXML
    Label errorNotFound;

    @FXML
    Label errorLobbyFull;

    public void handleJoinGame() throws IOException, InterruptedException {
        Task<JoinResultState> task= new Task<>(){
            @Override
            protected JoinResultState call() throws Exception {
                return  viewmodel.tryJoin(getCode());
            }
        };

        task.setOnSucceeded(  e->{
            JoinResultState result= task.getValue();
              if(result== JoinResultState.NOT_FOUND) {
                  JoinLabel.setVisible(false);
                  errorNotFound.setVisible(true);
                  errorLobbyFull.setVisible(false);
              } else
                  if(result== JoinResultState.LOBBY_FULL){
                      JoinLabel.setVisible(false);
                      errorNotFound.setVisible(false);
                      errorLobbyFull.setVisible(true);
                  } else{
                      try{
                          JoinLabel.setVisible(false);
                          navigateToLobby();
                      } catch (IOException | InterruptedException ex) {
                          throw new RuntimeException(ex); }
                   }
                });

        task.setOnFailed( e->{
            JoinLabel.setVisible(false);
            errorNotFound.setText("Network error. Try again.");
            errorNotFound.setVisible(true);

        });

        JoinLabel.setVisible(true);
        new Thread(task).start();
    }

    private void navigateToLobby() throws IOException, InterruptedException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Lobby.fxml"));
        Parent root = loader.load();
        Lobbycontroller controller = loader.getController();
        controller.setupAsJoiner(getCode());
        Stage lobbyStage = new Stage();
        lobbyStage.initModality(Modality.APPLICATION_MODAL);
        lobbyStage.setResizable(false);
        lobbyStage.setScene(new Scene(root));
        lobbyStage.show();
    }

    private String getCode() {
        return Code_enter.getText();
    }
}
