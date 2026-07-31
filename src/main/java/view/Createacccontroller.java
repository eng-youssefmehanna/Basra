package main.java.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import main.java.view.widgetview.LabeledPassFieldcontroller;
import main.java.view.widgetview.LabeledTextFieldcontroller;

import java.io.IOException;

public class Createacccontroller {
@FXML
LabeledTextFieldcontroller LabeledTextField_include1_createaccController;
@FXML
LabeledTextFieldcontroller LabeledTextField_include2_createaccController;
@FXML
LabeledPassFieldcontroller LabeledPassField_include1_createaccController;
@FXML
LabeledPassFieldcontroller LabeledPassField_include2_createaccController;

@FXML
    Button Createaccount_button;

/* need to check for identical passwords for confirm passwords
* check for redundat username
* enforce password requirments
* what if wrong case
* */

public void initialize(){
    LabeledTextField_include1_createaccController.setLabel("Your Name:");
    LabeledTextField_include2_createaccController.setLabel("Username:");
    LabeledPassField_include1_createaccController.setLabel("Password");
    LabeledPassField_include2_createaccController.setLabel("Confirm password");
    LabeledPassField_include1_createaccController.setPromptText("min: 6 chars, 1 special char requiered");
}

    public String getFullName() {
        return LabeledTextField_include1_createaccController.getText();
    }

    public String getUserName() {
        return LabeledTextField_include2_createaccController.getText();
    }

    public String getPassword() {
        return LabeledPassField_include1_createaccController.getText();
    }

    public String getConfirmPassword() {
        return LabeledPassField_include2_createaccController.getText();
    }

    public void handleAccountCreation(){
        Stage Createacc_CurrentStage = (Stage) Createaccount_button.getScene().getWindow();
        Createacc_CurrentStage.close();

    }







}
