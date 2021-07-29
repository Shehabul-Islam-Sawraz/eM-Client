package Model;

public class EmailSizeModifier implements Comparable<EmailSizeModifier>{
    private int size;

    public EmailSizeModifier(int size) {
        this.size = size;
    }

    @Override
    public String toString(){
        if (size <= 0) {
            return "0";
        } else if (size < 1024) {
            return size + " B";
        } else if (size < 1048576) {
            return size / 1024 + " kB";
        } else {
            return size / 1048576 + " MB";
        }
    }

    @Override
    public int compareTo(EmailSizeModifier email) {
        if(size > email.size) {
            return 1;
        } else if(email.size > size) {
            return -1;
        } else {
            return 0;
        }
    }
}
