package sample;

import FXMLS.ViewFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Controller.LoginController;

public class Main extends Application {
    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage=primaryStage;
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

        ViewFactory viewFactory = new ViewFactory(new EmailManager());
        viewFactory.showLoginWindow();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
