package sample;

import Model.EmailAccount;
import javafx.scene.control.TreeItem;

public class EmailManager {
    private TreeItem<String> foldersRoot = new TreeItem<String>("");

    public TreeItem<String> getFoldersRoot(){
        return foldersRoot;
    }

    public void addEmailAccount(EmailAccount emailAccount){
        TreeItem<String> treeItem = new TreeItem<String>(emailAccount.getEmailAddress());
        treeItem.setExpanded(true);
        treeItem.getChildren().add(new TreeItem<String>("INBOX"));
        treeItem.getChildren().add(new TreeItem<String>("Sent"));
        treeItem.getChildren().add(new TreeItem<String>("Folder1"));
        treeItem.getChildren().add(new TreeItem<String>("Spam"));
        foldersRoot.getChildren().add(treeItem);
    }
}
