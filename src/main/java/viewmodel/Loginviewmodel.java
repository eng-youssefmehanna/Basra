package main.java.viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import main.java.model.Player;
import main.java.model.state.RegistrationState;
import main.java.repository.UserRepository;

import java.io.IOException;

public class Loginviewmodel {
   private UserRepository user;


    public Loginviewmodel(UserRepository user){
        this.user=user;

    }

    public Player allowLogin(String username,String password) throws IOException, InterruptedException {
        return user.fetchUser(username,password);
    }
}
