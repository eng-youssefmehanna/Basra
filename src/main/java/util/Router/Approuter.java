package main.java.util.Router;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Approuter {

    public static void closeStage(Stage stage){
        stage.close();
    }

    //IO exception cause reading a file
    public static void openCreateAccount() throws IOException {
        FXMLLoader loader=new FXMLLoader(Approuter.class.getResource("/fxml/Createacc.fxml"));
        Parent root = loader.load();
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setResizable(false);
        popupStage.setScene(new Scene(root));
        popupStage.showAndWait();
    }

    public static void navigateToHome(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(Approuter.class.getResource("/fxml/Home.fxml"));
        Parent root = loader.load();
        Stage homeScreen = new Stage();
        homeScreen.setScene(new Scene(root));
        stage.close();
        homeScreen.show();
    }
}
