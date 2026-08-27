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
import main.java.util.Router.Approuter;
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
    private Label networkError;


    @FXML
    public void initialize() {
        loginviewModel.getLoginResult().addListener((obs, old, newResult) -> {
            switch (newResult.state()) {
                case LOADING     -> Load.setVisible(true);
                case SUCCESS     -> {
                    Load.setVisible(false);
                    SessionManager.setCurrentPlayer(newResult.player());
                    try { Approuter.navigateToHome(getStage()); }
                    catch (IOException e) { throw new RuntimeException(e); }
                }
                case ERRORLABEL  -> { Load.setVisible(false);
                                   errorLabel.setVisible(true); }
                case NETWORKERROR-> { Load.setVisible(false);
                                     networkError.setVisible(true); }
                case IDLE        -> {}
            }
        });
    }

    public void handleSignIn() {
        loginviewModel.login(getUsername(), getPassword());
    }

    public String getUsername(){
        return usernameField.getText();
    }

    public String getPassword(){
        return passwordField.getText();
    }

    public void handleCreateAccount() throws IOException {
        Approuter.openCreateAccount();
    }
   public Stage getStage(){
        return (Stage) LoginButton.getScene().getWindow();
   }

}
