package main.java.view.widgetview;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Middle_Homecontroller {
@FXML
Label Welcome_Home;

@FXML
    Button Start_game;

@FXML
    Button Join_game;

    private Runnable onStartGame;

    public void setOnStartGame(Runnable action) {
        this.onStartGame = action;
    }

    @FXML
    public void handleStartGame() {
        if(onStartGame != null) onStartGame.run();
    }

    public StringProperty welcomeTextProperty() {
        return Welcome_Home.textProperty();
    }
    

    @FXML
    public void handleJoinGame(){

    }


}
