package main.java.view;

import javafx.fxml.FXML;
import main.java.view.widgetview.Label1_Label2dynamiccontroller;
import main.java.view.widgetview.Header_Homecontroller;
import main.java.view.widgetview.Middle_Homecontroller;
import main.java.view.widgetview.*;

public class Homecontroller {
    @FXML
    Label1_Label2dynamiccontroller Label1_Label2dynamic_include1Controller;

    @FXML
    Label1_Label2dynamiccontroller Label1_Label2dynamic_include2Controller;

    @FXML
    Header_Homecontroller Header_Home_IncludeController;

    @FXML
    Middle_Homecontroller Middle_Home_IncludeController;


    public void initialize(){
        Label1_Label2dynamic_include1Controller.setLabel("\uD83C\uDFC6 Wins: ");
        Label1_Label2dynamic_include1Controller.setLabelDynamic("2");
        Label1_Label2dynamic_include2Controller.setLabel("♣ Games:");
        Label1_Label2dynamic_include2Controller.setLabelDynamic("1");
        Header_Home_IncludeController.setUserName("test_username");
        Middle_Home_IncludeController.setWelcomeText("User_Firstname");

    }
}
