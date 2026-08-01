package main.java.view;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.config.SessionManager;
import main.java.model.Player;
import main.java.repository.UserRepository;
import main.java.viewmodel.Loginviewmodel;

import java.io.IOException;


public class Logincontroller {

    //
    private Loginviewmodel loginviewModel = new Loginviewmodel(new UserRepository());
    @FXML
   private Button LoginButton;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;
   @FXML
    private Label Load;



    @FXML
    public void initialize(){

    }

    public void handleSignIn() {
        Task<Player> task=new Task<>(){
            @Override
            protected Player call() throws Exception {
                return loginviewModel.allowLogin(getUsername(), getPassword());
            }
        };

        task.setOnSucceeded(e -> {
            Player player = task.getValue();
            if(player == null) {
                Load.setVisible(false);
               // for debugging: System.out.println("showing error label");
                errorLabel.setVisible(true);
            } else {
                try {
                    SessionManager.setCurrentPlayer(player);
                    navigateToHome(); }
                catch (IOException ex) { throw new RuntimeException(ex); }
            }
        });

                task.setOnFailed(e -> {
                        errorLabel.setVisible(true);
                    });


                  Load.setVisible(true);
          new Thread(task).start();
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

    private void navigateToHome() throws IOException {
        Stage currentStage = (Stage) LoginButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Home.fxml"));
        Parent root = loader.load();
        Stage homeScreen = new Stage();
        homeScreen.setScene(new Scene(root));
        currentStage.close();
        homeScreen.show();
    }

}
