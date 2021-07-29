package sample;

import Controller.EmailServices.FetchFoldersService;
import Model.EmailAccount;
import Model.EmailTreeItem;
import javafx.scene.control.TreeItem;

public class EmailManager {
    private EmailTreeItem<String> foldersRoot = new EmailTreeItem(""); // Custom Tree Item

    public TreeItem<String> getFoldersRoot(){
        return foldersRoot;
    }

    public void addEmailAccount(EmailAccount emailAccount){
        EmailTreeItem<String> treeItem = new EmailTreeItem<String>(emailAccount.getEmailAddress());
        FetchFoldersService fetchFoldersService = new FetchFoldersService(emailAccount.getStore(), treeItem);
        fetchFoldersService.start();
        foldersRoot.getChildren().add(treeItem);
    }
}
