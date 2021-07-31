package sample;

import Controller.EmailServices.LoginService;
import Controller.Persistence.PersistenceAccess;
import Controller.Persistence.ValidAccount;
import FXMLS.ViewFactory;
import Model.EmailAccount;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Controller.LoginController;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    private Stage stage;
    private PersistenceAccess persistenceAccess = new PersistenceAccess();
    private EmailManager emailManager = new EmailManager();

    @Override
    public void start(Stage primaryStage) throws Exception{
        //stage=primaryStage;
        /*Parent root = FXMLLoader.load(getClass().getResource("/FXMLS/login.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.show();*/

        /*FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXMLS/login.fxml"));
        Parent root = loader.load();
        //LoginController controller = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();*/

        ViewFactory viewFactory = new ViewFactory(emailManager);
        List<ValidAccount> validAccountList = persistenceAccess.loadFromPersistence();
        if(validAccountList.size() > 0) {
            viewFactory.showMainWindow();
            for (ValidAccount validAccount: validAccountList){
                EmailAccount emailAccount = new EmailAccount(validAccount.getAddress(), validAccount.getPassword());
                LoginService loginService = new LoginService(emailAccount, emailManager);
                loginService.start();
            }
        } else {
            viewFactory.showLoginWindow();
        }
    }

    @Override
    public void stop() throws Exception {
        List<ValidAccount> validAccountList = new ArrayList<ValidAccount>();
        for (EmailAccount emailAccount: emailManager.getEmailAccounts()){
            validAccountList.add(new ValidAccount(emailAccount.getEmailAddress(), emailAccount.getPassword()));
        }
        persistenceAccess.saveToPersistence(validAccountList);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
