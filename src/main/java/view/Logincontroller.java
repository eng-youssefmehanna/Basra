package main.java.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/*
*  check is student credentials are valid
*  what if wrong case

* */
public class Logincontroller {


    @FXML
   private Button LoginButton;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;


    @FXML
    public void initialize(){

    }

    public void handleSignIn() throws IOException {
        Stage Login_CurrentStage = (Stage) LoginButton.getScene().getWindow();
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/fxml/Home.fxml"));
        Parent root = loader.load();
        Stage homeScreen = new Stage();
        homeScreen.setScene(new Scene(root));
        Login_CurrentStage.close();
        homeScreen.show();
    }

//IO exception cause reading a file
    public void handleCreateAccount() throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/fxml/Createacc.fxml"));
        Parent root = loader.load();
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setResizable(false);
        popupStage.setScene(new Scene(root));
        popupStage.showAndWait();
    }

    public String getUsername(){
       return usernameField.getText();
    }

    public String getPassword(){
        return passwordField.getText();
    }




}
