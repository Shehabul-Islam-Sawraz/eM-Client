package FXMLS;

import Controller.BaseController;
import Controller.LoginController;
import Controller.MainWindowController;
import Controller.UIUpdateController;
import UI.ColorTheme;
import UI.FontSize;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.EmailManager;

import java.io.IOException;

public class ViewFactory {
    private EmailManager emailManager;
    //UI options handling
    private FontSize fontSize = FontSize.MEDIUM;
    private ColorTheme colorTheme = ColorTheme.DEFAULT;

    public FontSize getFontSize() {
        return fontSize;
    }

    public void setFontSize(FontSize fontSize) {
        this.fontSize = fontSize;
    }

    public ColorTheme getColorTheme() {
        return colorTheme;
    }

    public void setColorTheme(ColorTheme colorTheme) {
        this.colorTheme = colorTheme;
    }

    public ViewFactory(EmailManager emailManager) {
        this.emailManager = emailManager;
    }


    public void showLoginWindow(){
        BaseController controller = new LoginController(emailManager,this,"login.fxml");
        initializeScene(controller);
    }

    public void showMainWindow(){
        BaseController controller = new MainWindowController(emailManager,this,"mainWindow.fxml");
        initializeScene(controller);
    }

    public void showUIUpdateWindow(){
        BaseController controller = new UIUpdateController(emailManager,this,"uiUpdate.fxml");
        initializeScene(controller);
    }

    private void initializeScene(BaseController controller) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(controller.getFxmlName()));
        loader.setController(controller);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void closeStage(Stage stage){
        stage.close();
    }

}
