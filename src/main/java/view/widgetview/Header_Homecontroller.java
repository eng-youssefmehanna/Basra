package main.java.view.widgetview;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;

public class Header_Homecontroller {
    @FXML
    Circle Circle_Home;
    @FXML
    Label Label_username_Home;


   public void setUserName(String text) {

       Label_username_Home.setText(text);
    }

    @FXML
    public void handleAvatarClick(){

    }
}
