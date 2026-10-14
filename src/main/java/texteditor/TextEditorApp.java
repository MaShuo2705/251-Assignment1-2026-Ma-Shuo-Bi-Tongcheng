package texteditor;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Application entry point of the 251 Assignment 1 text editor.
 *
 * <p>This class only wires the Swing GUI up on the Event Dispatch Thread.
 * All the actual functionality lives in the {@link texteditor.gui.EditorFrame}
 * and the YAML configuration module in {@code texteditor.config}.</p>
 *
 * <p>Written by <b>Member A</b> (Bi Tongcheng, 25014402).</p>
 *
 * @author Bi Tongcheng (25014402)
 * @version 1.0.0
 */
public final class TextEditorApp {

    /** Private constructor: this is a launcher class. */
    private TextEditorApp() {
    }

    /**
     * Program entry point.
     *
     * @param args command line arguments (unused)
     */
    public static void main(String[] args) {
        // Use the system look & feel so the editor matches the OS theme.
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Falling back to the default Metal look & feel is harmless.
            System.err.println("[TextEditorApp] could not set look & feel: " + e.getMessage());
        }

        // Swing components must be created and updated on the EDT.
        SwingUtilities.invokeLater(() -> texteditor.gui.EditorFrame.main(args));
    }
}
