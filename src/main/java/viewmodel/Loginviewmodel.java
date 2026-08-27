package main.java.viewmodel;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import main.java.model.Player;
import main.java.model.state.LoginState;
import main.java.repository.UserRepository;
import main.java.util.AppExecutor;
import main.java.viewmodel.result.LoginResult;

import java.io.IOException;

public class Loginviewmodel {
   private UserRepository user;
   private ObjectProperty<LoginResult> loginResult= new SimpleObjectProperty<>(new LoginResult(
           LoginState.IDLE,null)
   );
   public Loginviewmodel(UserRepository user){
        this.user=user;
    }

    public void login(String username, String password){
        Platform.runLater(()-> loginResult.set(new LoginResult(LoginState.LOADING, null)));
        AppExecutor.executor.submit(()-> {
            try {
                Player result = allowLogin(username, password);
                if (result == null) {
                    Platform.runLater(() -> loginResult.set(new LoginResult(LoginState.ERRORLABEL, null)));
                } else {
                    Platform.runLater(() ->loginResult.set(new LoginResult(LoginState.SUCCESS, result)));
                }
            } catch (IOException | InterruptedException e) {
                Platform.runLater(() -> loginResult.set(new LoginResult(LoginState.NETWORKERROR, null)));
            }
        });
    }

    private Player allowLogin(String username,String password) throws IOException, InterruptedException {
        return user.fetchUser(username,password);
    }

    public ObjectProperty<LoginResult> getLoginResult() {
        return loginResult;
    }

}
