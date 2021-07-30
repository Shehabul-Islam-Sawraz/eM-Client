package sample;

import Controller.EmailServices.FetchFoldersService;
import Controller.EmailServices.FolderUpdateService;
import Model.EmailAccount;
import Model.EmailMessage;
import Model.EmailTreeItem;
import javafx.scene.control.TreeItem;

import javax.mail.Flags;
import javax.mail.Folder;
import java.util.ArrayList;
import java.util.List;

public class EmailManager {
    private EmailTreeItem<String> foldersRoot = new EmailTreeItem(""); // Custom Tree Item
    private FolderUpdateService updateService;
    private List<Folder> folderList = new ArrayList<>();

    private EmailMessage selectedEmail;
    private EmailTreeItem<String> selectedFolder;

    public EmailManager(){
        updateService = new FolderUpdateService(folderList);
        updateService.start();
    }

    public EmailMessage getSelectedEmail() {
        return selectedEmail;
    }

    public void setSelectedEmail(EmailMessage selectedEmail) {
        this.selectedEmail = selectedEmail;
    }

    public EmailTreeItem<String> getSelectedFolder() {
        return selectedFolder;
    }

    public void setSelectedFolder(EmailTreeItem<String> selectedFolder) {
        this.selectedFolder = selectedFolder;
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

    public void setRead() {
        try {
            selectedEmail.setRead(true);
            selectedEmail.getMessage().setFlag(Flags.Flag.SEEN, true);
            selectedFolder.decrementMessagesCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUnRead() {
        try {
            selectedEmail.setRead(false);
            selectedEmail.getMessage().setFlag(Flags.Flag.SEEN, false);
            selectedFolder.incrementMessagesCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteSelectedMessage() {
        try {
            selectedEmail.getMessage().setFlag(Flags.Flag.DELETED, true);
            selectedFolder.getEmailMessages().remove(selectedEmail);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
