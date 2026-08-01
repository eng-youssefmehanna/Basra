package main.java.viewmodel;

import main.java.model.Player;
import main.java.repository.UserRepository;

import java.io.IOException;

public class Createaccount_viewmodel {

    private UserRepository user;

    public Createaccount_viewmodel(UserRepository user){
        this.user=user;
    }

    public boolean allowRegistration(String name, String username, String password) throws IOException, InterruptedException {
        Player player= new Player(name,username,password);
       return user.registerUser(player);
    }


}
