package main.java.view;

import javafx.fxml.FXML;
import main.java.view.widgetview.Label1_Label2dynamiccontroller;
import main.java.view.widgetview.Header_Homecontroller;
import main.java.view.widgetview.Middle_Homecontroller;
import main.java.view.widgetview.*;
import main.java.viewmodel.Homeviewmodel;

//usernameLabel.textProperty().bind(viewModel.userNameProperty());
public class Homecontroller {
    @FXML
    Label1_Label2dynamiccontroller Label1_Label2dynamic_include1Controller;

    @FXML
    Label1_Label2dynamiccontroller Label1_Label2dynamic_include2Controller;

    @FXML
    Header_Homecontroller Header_Home_IncludeController;

    @FXML
    Middle_Homecontroller Middle_Home_IncludeController;


    private Homeviewmodel viewModel = new Homeviewmodel();

    public void initialize() {
        Header_Home_IncludeController.userNameProperty().bind(viewModel.userNameProperty());
        Middle_Home_IncludeController.welcomeTextProperty().bind(viewModel.firstNameProperty());

        Label1_Label2dynamic_include1Controller.setLabel("🏆 Wins: ");
        Label1_Label2dynamic_include1Controller.dynamicLabelProperty().bind(viewModel.numWinsProperty());
        Label1_Label2dynamic_include2Controller.setLabel("♣ Games:");
        Label1_Label2dynamic_include2Controller.dynamicLabelProperty().bind(viewModel.numGamesProperty());
    }
}
