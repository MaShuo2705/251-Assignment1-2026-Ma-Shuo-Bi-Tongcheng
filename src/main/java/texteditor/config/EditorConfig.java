package texteditor.config;

import java.awt.Color;

/**
 * Immutable holder of the text editor configuration values.
 *
 * <p>This class is the in-memory representation of the YAML configuration
 * file {@code editor-config.yaml}. It is populated by
 * {@link EditorConfigLoader} and then used by the GUI ({@code EditorFrame})
 * to apply the default appearance and behaviour of the editor.</p>
 *
 * <p>All colour values are stored as Java {@link Color} objects so that the
 * Swing components can consume them directly.</p>
 *
 * @author Bi Tongcheng (25014402)
 * @version 1.0.0
 */
public final class EditorConfig {

    // ---- Window defaults ----
    private final String title;
    private final int width;
    private final int height;
    private final int x;
    private final int y;
    private final String defaultFolder;

    // ---- Editor defaults ----
    private final String fontFamily;
    private final int fontSize;
    private final Color fontColor;
    private final Color backgroundColor;
    private final Color caretColor;
    private final boolean wrapLine;
    private final int tabSize;

    // ---- Search defaults ----
    private final boolean caseSensitive;
    private final Color highlightColor;

    // ---- About / metadata ----
    private final String aboutTitle;
    private final String group;
    private final String version;

    /**
     * Constructs a fully populated configuration object.
     * Values are supplied by the YAML loader; the caller is responsible for
     * providing defaults when a key is missing.
     */
    public EditorConfig(String title, int width, int height, int x, int y,
                        String defaultFolder, String fontFamily, int fontSize,
                        Color fontColor, Color backgroundColor, Color caretColor,
                        boolean wrapLine, int tabSize, boolean caseSensitive,
                        Color highlightColor, String aboutTitle, String group,
                        String version) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.defaultFolder = defaultFolder;
        this.fontFamily = fontFamily;
        this.fontSize = fontSize;
        this.fontColor = fontColor;
        this.backgroundColor = backgroundColor;
        this.caretColor = caretColor;
        this.wrapLine = wrapLine;
        this.tabSize = tabSize;
        this.caseSensitive = caseSensitive;
        this.highlightColor = highlightColor;
        this.aboutTitle = aboutTitle;
        this.group = group;
        this.version = version;
    }

    // ---- Getters ----

    public String getTitle() {
        return title;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getDefaultFolder() {
        return defaultFolder;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public int getFontSize() {
        return fontSize;
    }

    public Color getFontColor() {
        return fontColor;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public Color getCaretColor() {
        return caretColor;
    }

    public boolean isWrapLine() {
        return wrapLine;
    }

    public int getTabSize() {
        return tabSize;
    }

    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    public Color getHighlightColor() {
        return highlightColor;
    }

    public String getAboutTitle() {
        return aboutTitle;
    }

    public String getGroup() {
        return group;
    }

    public String getVersion() {
        return version;
    }

    /** Returns a human readable summary of the loaded configuration. */
    @Override
    public String toString() {
        return "EditorConfig{title='" + title + "', size=" + width + "x" + height
                + ", font=" + fontFamily + " " + fontSize + "pt"
                + ", fg=" + fontColor + ", bg=" + backgroundColor + "}";
    }
}
