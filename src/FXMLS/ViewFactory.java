package FXMLS;

import Controller.BaseController;
import Controller.LoginController;
import Controller.MainWindowController;
import Controller.UIUpdateController;
import Controller.ComposeEmailController;
import UI.ColorTheme;
import UI.FontSize;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Controller;
import sample.EmailManager;

import java.io.IOException;
import java.util.ArrayList;

public class ViewFactory {
    private EmailManager emailManager;
    private ArrayList<Stage> activeStages;
    private boolean isMainWindowOpen=false;
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

    public boolean isMainWindowOpen() {
        return isMainWindowOpen;
    }

    public void setMainWindowOpen(boolean mainWindowOpen) {
        this.isMainWindowOpen = mainWindowOpen;
    }

    public ViewFactory(EmailManager emailManager) {
        this.emailManager = emailManager;
        activeStages = new ArrayList<>();
    }


    public void showLoginWindow(){
        BaseController controller = new LoginController(emailManager,this,"login.fxml");
        initializeScene(controller);
    }

    public void showMainWindow(){
        BaseController controller = new MainWindowController(emailManager,this,"mainWindow.fxml");
        initializeScene(controller);
        isMainWindowOpen=true;
    }

    public void showUIUpdateWindow(){
        BaseController controller = new UIUpdateController(emailManager,this,"uiUpdate.fxml");
        initializeScene(controller);
    }

    public void showComposeEmailWindow(){
        BaseController controller = new ComposeEmailController(emailManager,this,"ComposeEmail.fxml");
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
        activeStages.add(stage);
        stage.show();
    }

    public void closeStage(Stage stage){
        activeStages.remove(stage);
        stage.close();
    }

    public void updateUI() {
        for(Stage stage:activeStages){
            Scene scene = stage.getScene();
            //handle css
            scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource(ColorTheme.getCssPath(colorTheme)).toExternalForm());
            scene.getStylesheets().add(getClass().getResource(FontSize.getCssPath(fontSize)).toExternalForm());
        }
    }
}
