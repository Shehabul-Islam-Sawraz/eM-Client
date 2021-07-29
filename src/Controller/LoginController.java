package Controller;

import Controller.EmailServices.EmailLoginServiceResult;
import Controller.EmailServices.LoginService;
import FXMLS.ViewFactory;
import Model.EmailAccount;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
        if(checkEmailAndPass()){
            EmailAccount emailAccount = new EmailAccount(email.getText(),password.getText());
            LoginService loginService = new LoginService(emailAccount,emailManager);
            loginService.start();
            loginService.setOnSucceeded(e->{
                EmailLoginServiceResult result = loginService.getValue();
                switch (result) {
                    case SUCCESS:
                        System.out.println("Login successful!!!" + emailAccount);
                        Stage stage = (Stage)errorMsg.getScene().getWindow();
                        viewFactory.closeStage(stage);
                        if(!viewFactory.isMainWindowOpen()){
                            viewFactory.showMainWindow();
                        }
                        return;
                    case FAILED_BY_CREDENTIALS:
                        errorMsg.setText("Invalid Credentials!!");
                        return;
                    case FAILED_BY_UNEXPECTED_ERROR:
                        errorMsg.setText("Unexpected Error!!");
                        return;
                    default:
                        return;
                }
            });
        }
    }

    private boolean checkEmailAndPass(){
        if(email.getText().isEmpty()){
            errorMsg.setText("Please provide your email address!!");
            return false;
        }
        if(password.getText().isEmpty()){
            errorMsg.setText("Please enter the password!!");
            return false;
        }
        return true;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
