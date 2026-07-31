package main.java.view.widgetview;

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

    public void setWelcomeText(String text) {

        Welcome_Home.setText("Welcome " +text +"!");
    }

    @FXML
    public void handleStartGame(){

    }

    @FXML
    public void handleJoinGame(){

    }


}
