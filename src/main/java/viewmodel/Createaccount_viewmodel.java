package main.java.viewmodel;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import main.java.model.Player;
import main.java.model.state.RegistrationState;
import main.java.repository.UserRepository;
import main.java.util.AppExecutor;

import java.io.IOException;

public class Createaccount_viewmodel {

    private UserRepository user;
    private ObjectProperty<RegistrationState> regState=new SimpleObjectProperty<>(RegistrationState.IDLE);


    public Createaccount_viewmodel(UserRepository user){

        this.user=user;
    }

    public void register(String name, String username, String password){
        Platform.runLater(()->regState.set(RegistrationState.LOADING));
        AppExecutor.executor.submit(()->{
            try{
                boolean result= allowRegistration(name, username, password);
                Platform.runLater(()-> regState.set(
                      result? RegistrationState.SUCCESS : RegistrationState.USERNAMETAKEN
                ));
            } catch(IllegalArgumentException e){
                Platform.runLater(()-> regState.set(RegistrationState.PASSWORDREQS));
            }catch (Exception e) {
                Platform.runLater(() -> regState.set(RegistrationState.NETWORKERROR));
            }
                });
    }

    private boolean allowRegistration(String name, String username, String password) throws IOException, InterruptedException {
        Player player= new Player(name,username,password); //password throws illegal arg exception
       return user.registerUser(player);
    }

    public ObjectProperty<RegistrationState> getRegState(){

        return regState;
    }

    public void setRegState(RegistrationState state) {
        regState.set(state);
    }


}
