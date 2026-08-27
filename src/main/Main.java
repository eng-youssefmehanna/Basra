package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.model.Card;
import main.java.model.Ranks;
import main.java.model.Suits;

public class Main extends Application {
            @Override
            public void start(Stage stage) throws Exception {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/main/resources/fxml/Login.fxml")
                );
                Parent root = loader.load();
                stage.setScene(new Scene(root));
                stage.setTitle("Basra");
                stage.show();
                Card c = new Card(Suits.SPADES, Ranks.ACE);
                System.out.println(c.getImageUrl());

            }


        }

