package main.java.view.widgetview;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Label1_Label2dynamiccontroller {
    @FXML
    Label Label1_static;

    @FXML
    Label Label2_dynamic;

    public void setLabel(String text) {
        Label1_static.setText(text);
    }

    public StringProperty dynamicLabelProperty() {
        return Label2_dynamic.textProperty();
    }
}
