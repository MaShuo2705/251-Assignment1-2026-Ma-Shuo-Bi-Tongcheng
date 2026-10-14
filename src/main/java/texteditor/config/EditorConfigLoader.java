package texteditor.config;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Loads the YAML configuration file ({@code editor-config.yaml}) into an
 * {@link EditorConfig} object.
 *
 * <p>The configuration file is packaged inside the application resources
 * (under {@code src/main/resources}). It is located on the classpath and
 * read with the SnakeYAML library. Every missing or malformed value is
 * replaced with a sensible hard-coded default, so the editor always starts
 * even if the configuration file is partly broken.</p>
 *
 * @author Bi Tongcheng (25014402)
 * @version 1.0.0
 */
public final class EditorConfigLoader {

    /** Classpath location of the YAML configuration file. */
    public static final String CONFIG_RESOURCE = "editor-config.yaml";

    // ---- Fallback defaults (used when the YAML file is missing/broken) ----
    private static final String DEFAULT_TITLE = "251 Text Editor";
    private static final int DEFAULT_WIDTH = 900;
    private static final int DEFAULT_HEIGHT = 650;
    private static final int DEFAULT_X = 100;
    private static final int DEFAULT_Y = 60;
    private static final String DEFAULT_FOLDER = "";
    private static final String DEFAULT_FONT_FAMILY = "Monospaced";
    private static final int DEFAULT_FONT_SIZE = 14;
    private static final Color DEFAULT_FONT_COLOR = Color.BLACK;
    private static final Color DEFAULT_BG_COLOR = Color.WHITE;
    private static final Color DEFAULT_CARET_COLOR = new Color(0xCC0000);
    private static final boolean DEFAULT_WRAP = true;
    private static final int DEFAULT_TAB = 4;
    private static final boolean DEFAULT_CASE_SENSITIVE = false;
    private static final Color DEFAULT_HIGHLIGHT = Color.YELLOW;
    private static final String DEFAULT_ABOUT_TITLE = "About 251 Text Editor";
    private static final String DEFAULT_GROUP = "Bi Tongcheng (25014402) & Ma Shuo (25014500)";
    private static final String DEFAULT_VERSION = "1.0.0";

    /** Private constructor: this is a utility class. */
    private EditorConfigLoader() {
    }

    /**
     * Loads the YAML configuration from the classpath.
     *
     * @return the parsed configuration (never {@code null}; always falls back
     *         to built-in defaults where the file or a key is missing)
     */
    public static EditorConfig load() {
        Yaml yaml = new Yaml();
        try (InputStream in = EditorConfigLoader.class.getClassLoader()
                .getResourceAsStream(CONFIG_RESOURCE)) {
            if (in == null) {
                System.err.println("[EditorConfig] resource '" + CONFIG_RESOURCE
                        + "' not found on classpath; using defaults.");
                return defaults();
            }
            Object root = yaml.load(in);
            if (root instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> data = (Map<String, Object>) root;
                return buildConfig(data);
            }
            System.err.println("[EditorConfig] root of config is not a map; using defaults.");
            return defaults();
        } catch (IOException | YAMLException e) {
            // Configuration must never prevent the editor from starting.
            System.err.println("[EditorConfig] failed to read config, using defaults: " + e.getMessage());
            return defaults();
        }
    }

    /**
     * Builds an {@link EditorConfig} from the flat map produced by SnakeYAML.
     * Each section of the YAML file is converted to a {@link Map} and read
     * with defensive helpers that return defaults for missing values.
     */
    @SuppressWarnings("unchecked")
    private static EditorConfig buildConfig(Map<String, Object> data) {
        Map<String, Object> win = asMap(data.get("window"));
        Map<String, Object> ed  = asMap(data.get("editor"));
        Map<String, Object> search = asMap(data.get("search"));
        Map<String, Object> about = asMap(data.get("about"));

        // ---- window ----
        String title = str(win, "title", DEFAULT_TITLE);
        int width = intOf(win, "width", DEFAULT_WIDTH);
        int height = intOf(win, "height", DEFAULT_HEIGHT);
        int x = intOf(win, "x", DEFAULT_X);
        int y = intOf(win, "y", DEFAULT_Y);
        String folder = str(win, "defaultFolder", DEFAULT_FOLDER);

        // ---- editor ----
        String fontFamily = str(ed, "fontFamily", DEFAULT_FONT_FAMILY);
        int fontSize = intOf(ed, "fontSize", DEFAULT_FONT_SIZE);
        Color fontColor = color(ed, "fontColor", DEFAULT_FONT_COLOR);
        Color bgColor = color(ed, "backgroundColor", DEFAULT_BG_COLOR);
        Color caret = color(ed, "caretColor", DEFAULT_CARET_COLOR);
        boolean wrap = bool(ed, "wrapLine", DEFAULT_WRAP);
        int tabSize = intOf(ed, "tabSize", DEFAULT_TAB);

        // ---- search ----
        boolean caseSensitive = bool(search, "caseSensitive", DEFAULT_CASE_SENSITIVE);
        Color highlight = color(search, "highlightColor", DEFAULT_HIGHLIGHT);

        // ---- about ----
        String aboutTitle = str(about, "title", DEFAULT_ABOUT_TITLE);
        String group = str(about, "group", DEFAULT_GROUP);
        String version = str(about, "version", DEFAULT_VERSION);

        return new EditorConfig(title, width, height, x, y, folder, fontFamily,
                fontSize, fontColor, bgColor, caret, wrap, tabSize, caseSensitive,
                highlight, aboutTitle, group, version);
    }

    /** Returns the configuration object with all default values. */
    private static EditorConfig defaults() {
        return new EditorConfig(DEFAULT_TITLE, DEFAULT_WIDTH, DEFAULT_HEIGHT,
                DEFAULT_X, DEFAULT_Y, DEFAULT_FOLDER, DEFAULT_FONT_FAMILY,
                DEFAULT_FONT_SIZE, DEFAULT_FONT_COLOR, DEFAULT_BG_COLOR,
                DEFAULT_CARET_COLOR, DEFAULT_WRAP, DEFAULT_TAB,
                DEFAULT_CASE_SENSITIVE, DEFAULT_HIGHLIGHT, DEFAULT_ABOUT_TITLE,
                DEFAULT_GROUP, DEFAULT_VERSION);
    }

    // ---- defensive accessors ----

    private static Map<String, Object> asMap(Object value) {
        return value instanceof Map ? (Map<String, Object>) value : java.util.Collections.emptyMap();
    }

    private static String str(Map<String, Object> map, String key, String def) {
        Object v = map.get(key);
        return v == null ? def : String.valueOf(v);
    }

    private static int intOf(Map<String, Object> map, String key, int def) {
        Object v = map.get(key);
        if (v instanceof Number) {
            return ((Number) v).intValue();
        }
        if (v instanceof String) {
            try {
                return Integer.parseInt(((String) v).trim());
            } catch (NumberFormatException ignored) {
                // fall through to default
            }
        }
        return def;
    }

    private static boolean bool(Map<String, Object> map, String key, boolean def) {
        Object v = map.get(key);
        if (v instanceof Boolean) {
            return (Boolean) v;
        }
        if (v instanceof String) {
            return Boolean.parseBoolean(((String) v).trim());
        }
        return def;
    }

    /**
     * Parses a hexadecimal colour string such as {@code "#RRGGBB"} into a
     * {@link Color}. Returns the default if the value is missing or invalid.
     */
    private static Color color(Map<String, Object> map, String key, Color def) {
        Object v = map.get(key);
        if (v == null) {
            return def;
        }
        try {
            String hex = String.valueOf(v).trim();
            if (hex.startsWith("#")) {
                hex = hex.substring(1);
            }
            if (hex.length() != 6) {
                return def;
            }
            return new Color(Integer.parseInt(hex, 16));
        } catch (NumberFormatException e) {
            return def;
        }
    }
}
