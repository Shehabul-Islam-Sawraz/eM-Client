package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private Label errorMsg;

    @FXML
    void loginButtonPressed() {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
