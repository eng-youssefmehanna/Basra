package main.java.view.widgetview;

import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class Header_Homecontroller {
    @FXML
    Circle Circle_Home;
    @FXML
    Label Label_username_Home;


    public StringProperty userNameProperty() {
        return Label_username_Home.textProperty();
    }

    @FXML
    public void handleAvatarClick(){

    }

    public void setAvatar(String url) {
        Task<Image> task = new Task<>() {
            @Override
            protected Image call() {
                return new Image(url); // background loading
            }
        };
        task.setOnSucceeded(e -> {
            System.out.println("Image loaded: " + task.getValue());
            Circle_Home.setFill(new ImagePattern(task.getValue()));
        });

        task.setOnFailed( e-> {
            System.out.println("Image load failed: " + task.getException().getMessage());
                }

        );
        new Thread(task).start();
    }
}
