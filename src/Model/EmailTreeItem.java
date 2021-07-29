package Model;

import javafx.scene.control.TreeItem;

public class EmailTreeItem<String> extends TreeItem<String> {
    private String name;

    public EmailTreeItem(String name) {
        super(name);
        this.name = name;
    }
}
