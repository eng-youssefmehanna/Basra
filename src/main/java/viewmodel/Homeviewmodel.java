package main.java.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import main.java.config.SessionManager;

public class Homeviewmodel {
    private StringProperty userName=new SimpleStringProperty();
    private StringProperty firstname=new SimpleStringProperty();
    private StringProperty numWins=new SimpleStringProperty();
    private StringProperty numGames=new SimpleStringProperty();



    public Homeviewmodel(){
        userName.set(SessionManager.getCurrentPlayer().getUserName());
        firstname.set("Welcome " + firstNameExtract() + "!");
        numWins.set(String.valueOf(SessionManager.getCurrentPlayer().getWins()));
        numGames.set(String.valueOf(SessionManager.getCurrentPlayer().getGamesPlayed()));

    }

    public StringProperty userNameProperty() { return userName; };
    public StringProperty firstNameProperty() { return firstname; };
    public StringProperty numWinsProperty() { return numWins; };
    public StringProperty numGamesProperty() { return numGames; };

private String firstNameExtract(){
    String fullName = SessionManager.getCurrentPlayer().getName();
    return fullName.split(" ")[0];
}
}
