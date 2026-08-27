package main.java.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.view.widgetview.Label1_Label2dynamiccontroller;
import main.java.view.widgetview.Header_Homecontroller;
import main.java.view.widgetview.Middle_Homecontroller;
import main.java.view.widgetview.*;
import main.java.viewmodel.Homeviewmodel;

import java.io.IOException;

//usernameLabel.textProperty().bind(viewModel.userNameProperty());
public class Homecontroller {
    @FXML
    Label1_Label2dynamiccontroller Label1_Label2dynamic_include1Controller;

    @FXML
    Label1_Label2dynamiccontroller Label1_Label2dynamic_include2Controller;

    @FXML
    Header_Homecontroller Header_Home_IncludeController;

    @FXML
    Middle_Homecontroller Middle_Home_IncludeController;


    private Homeviewmodel viewModel = new Homeviewmodel();

    public void initialize()  {
        Header_Home_IncludeController.userNameProperty().bind(viewModel.userNameProperty());
        Middle_Home_IncludeController.welcomeTextProperty().bind(viewModel.firstNameProperty());
        Header_Home_IncludeController.setAvatar(viewModel.avatarProperty().get());


        Middle_Home_IncludeController.setOnStartGame(()->{
                try{
                    navigateToLobby();
        }catch(IOException e){
                    e.printStackTrace();
        }
        });

        Middle_Home_IncludeController.setOnJoinGame(()-> {
            try {
                navigateToJoin();
            } catch (IOException e) {
                e.printStackTrace();

            }
        });


        Label1_Label2dynamic_include1Controller.setLabel("🏆 Wins: ");
        Label1_Label2dynamic_include1Controller.dynamicLabelProperty().bind(viewModel.numWinsProperty());
        Label1_Label2dynamic_include2Controller.setLabel("♣ Games:");
        Label1_Label2dynamic_include2Controller.dynamicLabelProperty().bind(viewModel.numGamesProperty());
    }

    public void navigateToLobby() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Lobby.fxml"));
        Parent root = loader.load();
        Stage lobbyStage = new Stage();
        lobbyStage.initModality(Modality.APPLICATION_MODAL);
        lobbyStage.setResizable(false);
        lobbyStage.setScene(new Scene(root));
        lobbyStage.show();
    }

        private void navigateToJoin() throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Join.fxml"));
            Parent root = loader.load();
            Stage joinStage = new Stage();
            joinStage.initModality(Modality.APPLICATION_MODAL);
            joinStage.setResizable(false);
            joinStage.setScene(new Scene(root));
            joinStage.show();
        }
}


