package UI;

public enum FontSize {
    SMALL,
    MEDIUM,
    BIG;

    public static String getCssPath(FontSize fontSize){
        switch (fontSize){
            case SMALL:
                return "/CSS/fontSmall.css";
            case MEDIUM:
                return "/CSS/fontMedium.css";
            case BIG:
                return "/CSS/fontBig.css";
            default:
                return null;
        }
    }
}
