package main.java.viewmodel;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import main.java.model.Player;
import main.java.model.state.LoginState;
import main.java.repository.UserRepository;
import main.java.util.AppExecutor;

import java.io.IOException;

public class Loginviewmodel {
   private UserRepository user;
   private ObjectProperty<LoginState> loginState= new SimpleObjectProperty<>(LoginState.IDLE);
   private Player loggedInPlayer;
   public Loginviewmodel(UserRepository user){
        this.user=user;
    }

    public void login(String username, String password){
        Platform.runLater(()->loginState.set(LoginState.LOADING));
        AppExecutor.executor.submit(()-> {
            try {
                Player result = allowLogin(username, password);
                if (result == null) {
                    Platform.runLater(() -> loginState.set(LoginState.ERRORLABEL));
                } else {
                    loggedInPlayer=result;
                    Platform.runLater(() -> loginState.set(LoginState.SUCCESS));
                }
            } catch (IOException | InterruptedException e) {
                Platform.runLater(() -> loginState.set(LoginState.NETWORKERROR));
            }
        });
    }

    private Player allowLogin(String username,String password) throws IOException, InterruptedException {
        return user.fetchUser(username,password);
    }

    public ObjectProperty<LoginState>  getLoginState(){
        return loginState;
    }
    public Player getLoggedInPlayer() {
        return loggedInPlayer;
    }
}
