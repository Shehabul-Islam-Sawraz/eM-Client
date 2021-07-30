package Controller.EmailServices;

import Model.EmailTreeItem;
import Model.IconsResolver;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;
import javax.mail.event.MessageCountEvent;
import javax.mail.event.MessageCountListener;
import java.util.List;

public class FetchFoldersService extends Service<Void> {
    private Store store;
    private EmailTreeItem<String> foldersRoot;
    private List<Folder> folderList;
    private IconsResolver iconResolver = new IconsResolver();

    public FetchFoldersService(Store store, EmailTreeItem<String> foldersRoot, List<Folder> folderList) {
        this.store = store;
        this.foldersRoot = foldersRoot;
        this.folderList = folderList;
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
            folderList.add(folder);
            EmailTreeItem<String> emailTreeItem = new EmailTreeItem<String>(folder.getName());
            emailTreeItem.setGraphic(iconResolver.getIconForFolder(folder.getName()));
            foldersRoot.getChildren().add((emailTreeItem));
            foldersRoot.setExpanded(true);
            fetchMessagesOnFolder(folder, emailTreeItem);
            addMessageListenerToFolder(folder,emailTreeItem);
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

    private void addMessageListenerToFolder(Folder folder, EmailTreeItem<String> emailTreeItem) {
        folder.addMessageCountListener(new MessageCountListener() {
            @Override
            public void messagesAdded(MessageCountEvent messageCountEvent) {
                for (int i = 0; i < messageCountEvent.getMessages().length; i++){
                    try {
                        Message message = folder.getMessage(folder.getMessageCount() - i);
                        emailTreeItem.addEmailToTop(message);
                    } catch (MessagingException ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void messagesRemoved(MessageCountEvent messageCountEvent) {

            }
        });
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
                                    //System.out.println(folder.getMessage(i).getSubject());
                                    emailTreeItem.addEmail(folder.getMessage(i));
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
