package Controller.EmailServices;

import Model.EmailTreeItem;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Store;

public class FetchFoldersService extends Service<Void> {
    private Store store;
    private EmailTreeItem<String> foldersRoot;

    public FetchFoldersService(Store store, EmailTreeItem<String> foldersRoot) {
        this.store = store;
        this.foldersRoot = foldersRoot;
    }

    private void fetchFolders() {
        Folder[] folders = new Folder[0];
        try {
            folders = store.getDefaultFolder().list();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        handleFolders(folders, foldersRoot);
    }

    private void handleFolders(Folder[] folders, EmailTreeItem<String> foldersRoot) {
        for(Folder folder: folders){
            EmailTreeItem<String> emailTreeItem = new EmailTreeItem<String>(folder.getName());
            foldersRoot.getChildren().add((emailTreeItem));
            foldersRoot.setExpanded(true);
            fetchMessagesOnFolder(folder, emailTreeItem);
            try {
                if(folder.getType() == Folder.HOLDS_FOLDERS) {
                    Folder[] subFolders =  folder.list();
                    handleFolders(subFolders, emailTreeItem);
                }
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    private void fetchMessagesOnFolder(Folder folder, EmailTreeItem<String> emailTreeItem) {
        Service fetchMessagesService = new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Object call() {
                        try {
                            if(folder.getType() != Folder.HOLDS_FOLDERS){
                                folder.open(Folder.READ_WRITE);
                                int folderSize = folder.getMessageCount();
                                for(int i = folderSize; i > 0; i--){
                                    System.out.println(folder.getMessage(i).getSubject());
                                }
                            }
                        } catch (MessagingException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                };
            }
        };
        fetchMessagesService.start();
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                fetchFolders();
                return null;
            }
        };
    }
}
