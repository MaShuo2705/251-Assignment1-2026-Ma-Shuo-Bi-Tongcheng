package texteditor.config;

import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the YAML configuration loader.
 *
 * <p>These verify that the packaged {@code editor-config.yaml} file is read
 * correctly into an {@link EditorConfig} object, and that the loader never
 * returns {@code null} even when the resource is unavailable.</p>
 *
 * @author Bi Tongcheng (25014402)
 * @version 1.0.0
 */
class EditorConfigLoaderTest {

    /** The configuration file must be present and loaded. */
    @Test
    void loadsConfigFromResource() {
        EditorConfig cfg = EditorConfigLoader.load();
        assertNotNull(cfg, "configuration must never be null");
    }

    /** The YAML values must be reflected in the loaded configuration. */
    @Test
    void loadsYamlValues() {
        EditorConfig cfg = EditorConfigLoader.load();
        assertEquals("251 Text Editor", cfg.getTitle());
        assertTrue(cfg.getWidth() > 0);
        assertTrue(cfg.getHeight() > 0);
        assertEquals("Monospaced", cfg.getFontFamily());
        assertEquals(new Color(0x000000), cfg.getFontColor());
        assertEquals(new Color(0xFFFFFF), cfg.getBackgroundColor());
        assertTrue(cfg.getFontSize() > 0);
    }

    /** Group member information must be present for the About dialog. */
    @Test
    void aboutShowsBothMembers() {
        EditorConfig cfg = EditorConfigLoader.load();
        assertTrue(cfg.getGroup().contains("Bi Tongcheng"));
        assertTrue(cfg.getGroup().contains("Ma Shuo"));
    }
}
