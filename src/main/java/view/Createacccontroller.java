package main.java.view;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.java.model.state.RegistrationState;
import main.java.repository.UserRepository;
import main.java.util.Router.Approuter;
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
  Label networkError;

@FXML
 Label Load;

private Createaccount_viewmodel createAccVM = new Createaccount_viewmodel(new UserRepository());

/* need to check for identical passwords for confirm passwords/done
* check for redundant username/done
* enforce password requirments/done
* what if wrong case /done
* */

public void initialize(){
    LabeledTextField_include1_createaccController.setLabel("Your Name:");
    LabeledTextField_include2_createaccController.setLabel("Username:");
    LabeledPassField_include1_createaccController.setLabel("Password");
    LabeledPassField_include2_createaccController.setLabel("Confirm password");
    LabeledPassField_include1_createaccController.setPromptText("min: 6 chars, 1 special char requiered");
// old is idle, newState for reaction
    createAccVM.getRegState().addListener((obs, old, newState) -> {
        switch (newState) {
            case LOADING        -> Load.setVisible(true);
            case SUCCESS        -> { Load.setVisible(false);
                                    Approuter.closeStage(getStage()); }
            case EMPTYFIELDS    -> { Load.setVisible(false);
                                      errorEmptyFields.setVisible(true); }
            case PASSWORDMISMATCH -> { Load.setVisible(false);
                                      errorPasswordMatchs.setVisible(true); }
            case PASSWORDREQS   -> { Load.setVisible(false);
                                         errorPasswordReqs.setVisible(true); }
            case USERNAMETAKEN  -> { Load.setVisible(false);
                                        errorUsernameTaken.setVisible(true); }
            case NETWORKERROR   -> { Load.setVisible(false);
                                      networkError.setVisible(true); }
            case IDLE           -> {}
        }
    });
}

public void handleAccountCreation() {
    if (getFullName().isEmpty() || getUserName().isEmpty() || getPassword().isEmpty()) {
        createAccVM.setRegState(RegistrationState.EMPTYFIELDS);
        return;
    }
    if (!isConfirmPassword()) {
        createAccVM.setRegState(RegistrationState.PASSWORDMISMATCH);
        return;
    }
    createAccVM.register(getFullName(), getUserName(), getPassword());
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



private boolean isConfirmPassword(){

    return getPassword().equals(getConfirmPassword());
}

    private Stage getStage() {
    return (Stage) Createaccount_button.getScene().getWindow();
    }
}
