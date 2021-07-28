package UI;

public enum ColorTheme {
    LIGHT,
    DEFAULT,
    DARK;

    public static String getCssPath(ColorTheme colorTheme){
        switch (colorTheme){
            case LIGHT:
                return "/CSS/themeLight.css";
            case DEFAULT:
                return "/CSS/themeDefault.css";
            case DARK:
                return "/CSS/themeDark.css";
            default:
                return null;
        }
    }
}
