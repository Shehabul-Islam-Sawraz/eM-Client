package Controller;

import Controller.EmailServices.MessageRenderService;
import FXMLS.ViewFactory;
import Model.EmailMessage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebView;
import sample.EmailManager;

import java.net.URL;
import java.util.ResourceBundle;

public class EmailDetailsController extends BaseController implements Initializable {
    @FXML
    private WebView webView;

    @FXML
    private Label attachmentLabel;

    @FXML
    private Label subjectLabel;

    @FXML
    private Label senderLabel;

    @FXML
    private HBox hBoxDownloads;

    public EmailDetailsController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EmailMessage emailMessage = emailManager.getSelectedEmail();
        subjectLabel.setText(emailMessage.getSubject());
        senderLabel.setText(emailMessage.getSender());
        MessageRenderService messageRendererService = new MessageRenderService(webView.getEngine());
        messageRendererService.setEmailMessage(emailMessage);
        messageRendererService.restart();
    }
}
