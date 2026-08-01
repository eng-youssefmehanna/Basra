package main.java.view;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.java.repository.UserRepository;
import main.java.view.widgetview.LabeledPassFieldcontroller;
import main.java.view.widgetview.LabeledTextFieldcontroller;
import main.java.viewmodel.Createaccount_viewmodel;
import main.java.viewmodel.Loginviewmodel;

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

@FXML
    Label errorEmptyFields;
@FXML
    Label errorPasswordMatchs;
@FXML
    Label errorPasswordReqs;
@FXML
    Label errorUsernameTaken;

@FXML
 Label Load;

    private Createaccount_viewmodel createAccVM = new Createaccount_viewmodel(new UserRepository());

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

public void handleAccountCreation() {
    if(getFullName().isEmpty() || getUserName().isEmpty() || getPassword().isEmpty()) {
        errorEmptyFields.setVisible(true);
        return;
    }
    if(!isConfirmPassword()){
        errorPasswordMatchs.setVisible(true);
        return;
    }

    Task<Boolean> task= new Task<>(){
        @Override
        protected Boolean call() throws Exception {
           return createAccVM.allowRegistration(getFullName(),getUserName(),getPassword());
        }
    };


    task.setOnSucceeded( e-> {
        Load.setVisible(false);
        if(task.getValue()){
            navigationToLogin();
        } else{
            errorUsernameTaken.setVisible(true);
        }
    });

    task.setOnFailed( e-> {
        Load.setVisible(false);

        Throwable ex = task.getException();
        if(ex instanceof IllegalArgumentException) {
            // password didn't meet requirements in class Player
            errorPasswordReqs.setVisible(true);
        } else {
            // network error
            errorUsernameTaken.setVisible(true);
        }
    });

    Load.setVisible(true);
    new Thread(task).start();
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

    private void navigationToLogin (){
        Stage Createacc_CurrentStage = (Stage) Createaccount_button.getScene().getWindow();
        Createacc_CurrentStage.close();
}

private boolean isConfirmPassword(){
   return getPassword().equals(getConfirmPassword());
}
}
