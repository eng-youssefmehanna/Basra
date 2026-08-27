package main.java.view;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.config.SessionManager;
import main.java.repository.LobbyRealTimeClient;
import main.java.repository.Lobbyrepository;
import main.java.viewmodel.Lobbyviewmodel;

import java.io.IOException;
import java.util.List;

public class Lobbycontroller {
    @FXML
    Label code;

    @FXML
    VBox playersContainer;

    @FXML
    Button go_to_lobby;

    @FXML
    Label statusLabel;

    @FXML
    Button go_back;

    private Lobbyviewmodel viewModel;

    private LobbyRealTimeClient realtimeClient;

    @FXML
    public void initialize() throws IOException, InterruptedException {
        viewModel = new Lobbyviewmodel(new Lobbyrepository());
        code.textProperty().bind(viewModel.gameCodeProperty());

        for(String username : viewModel.getPlayers()) {
            addPlayerSlot(username);
        }

        try {
            realtimeClient = new LobbyRealTimeClient(
                    viewModel.gameCodeProperty().get().replace("Game code: ", ""),
                    this::updatePlayerSlots
            );
            realtimeClient.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void addPlayerSlot(String username) {
        HBox slot = new HBox();
        slot.setStyle("-fx-background-color: #ffffff; -fx-border-color: #c9a84c; " +
                "-fx-border-width: 1.5px; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-padding: 10;");
        slot.setMinHeight(44);
        Label nameLabel = new Label(username);
        System.out.println("label text: " + nameLabel.getText());
        nameLabel.setStyle("-fx-text-fill: #2a1f0a ; -fx-font-family: Georgia; -fx-font-size: 16px; -fx-font-weight: bold;");
        slot.getChildren().add(nameLabel);
        playersContainer.getChildren().add(slot);
    }

    public void setupAsJoiner(String code) throws IOException, InterruptedException {
        viewModel = new Lobbyviewmodel(new Lobbyrepository(), code);
        this.code.textProperty().bind(viewModel.gameCodeProperty());
        for (String username : viewModel.getPlayers()) {
            addPlayerSlot(username);
        }

        try {
            realtimeClient = new LobbyRealTimeClient(code, this::updatePlayerSlots);
            realtimeClient.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handlePlay() {
        statusLabel.setVisible(true);
    }

    @FXML
    public void handleGoBack() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                String code = viewModel.gameCodeProperty().get().replace("Game code: ", "");
                String username = SessionManager.getCurrentPlayer().getUserName();
                new Lobbyrepository().removeFromLobby(code, username);
                return null;
            }
        };
        task.setOnSucceeded(e -> {
            if (realtimeClient != null) {
                realtimeClient.close();
            }
            Stage stage = (Stage) go_back.getScene().getWindow();
            stage.close();
        });
        task.setOnFailed(e -> {
            if (realtimeClient != null) {
                realtimeClient.close();
            }
            Stage stage = (Stage) go_back.getScene().getWindow();
            stage.close();
        });
        new Thread(task).start();
    }

    private void updatePlayerSlots(List<String> players) {
        playersContainer.getChildren().clear();
        for (String username : players) {
            addPlayerSlot(username);
        }
    }
}
