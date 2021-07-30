package sample;

import Controller.EmailServices.FetchFoldersService;
import Controller.EmailServices.FolderUpdateService;
import Model.EmailAccount;
import Model.EmailTreeItem;
import javafx.scene.control.TreeItem;

import javax.mail.Folder;
import java.util.ArrayList;
import java.util.List;

public class EmailManager {
    private EmailTreeItem<String> foldersRoot = new EmailTreeItem(""); // Custom Tree Item
    private FolderUpdateService updateService;
    private List<Folder> folderList = new ArrayList<>();

    public EmailManager(){
        updateService = new FolderUpdateService(folderList);
        updateService.start();
    }

    public TreeItem<String> getFoldersRoot(){
        return foldersRoot;
    }

    public  List<Folder> getFolderList(){
        return this.folderList;
    }

    public void addEmailAccount(EmailAccount emailAccount){
        EmailTreeItem<String> treeItem = new EmailTreeItem<String>(emailAccount.getEmailAddress());
        FetchFoldersService fetchFoldersService = new FetchFoldersService(emailAccount.getStore(), treeItem,folderList);
        fetchFoldersService.start();
        foldersRoot.getChildren().add(treeItem);
    }
}
