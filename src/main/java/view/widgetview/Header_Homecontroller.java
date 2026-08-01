package main.java.view.widgetview;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
}
