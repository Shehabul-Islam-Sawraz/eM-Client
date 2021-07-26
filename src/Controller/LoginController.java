package Controller;

import FXMLS.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.EmailManager;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends BaseController implements Initializable {
    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private Label errorMsg;

    public LoginController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @FXML
    void loginButtonPressed() {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
