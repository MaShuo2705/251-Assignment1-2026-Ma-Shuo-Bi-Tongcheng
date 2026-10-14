package texteditor.gui;

import texteditor.config.EditorConfig;
import texteditor.io.DocumentLoader;
import texteditor.io.DocumentLoaderRegistry;
import texteditor.io.TextFileWriter;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * The main window of the text editor.
 *
 * <p>This class builds the GUI and the top menu bar with the required menus:
 * <b>File</b>, <b>Search</b>, <b>View</b> and <b>Help</b>. It implements the
 * base functionality assigned to <b>Member A</b>:</p>
 * <ul>
 *   <li>New (opens a fresh editor window)</li>
 *   <li>Open (reads standard {@code .txt} files via a file chooser)</li>
 *   <li>Save (writes the document to a {@code .txt} file)</li>
 *   <li>Exit (closes all windows and quits the program)</li>
 *   <li>Search (finds a word and highlights all matches)</li>
 *   <li>Select / Copy / Paste / Cut (SCPC)</li>
 *   <li>Time &amp; Date (inserts the current date/time at the top)</li>
 *   <li>About (shows both group members in a popup)</li>
 *   <li>Print (sends the document to the local printer)</li>
 * </ul>
 *
 * <p>The visual defaults (font colour, window size, etc.) come from the
 * YAML configuration module written by member A.</p>
 *
 * @author Bi Tongcheng (25014402)
 * @version 1.0.0
 */
public class EditorFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    /** All open editor windows, so Exit can close every one of them. */
    private static final List<EditorFrame> OPEN_FRAMES = new ArrayList<>();

    /** The loaded YAML configuration shared by every window. */
    private static EditorConfig config;

    /** The editing area of this window. */
    private final EditorPane editorPane;

    /** The file currently shown in this window (null if unsaved new file). */
    private File currentFile;

    /**
     * Loads the configuration once and opens an editor window.
     *
     * @param args command line arguments (unused)
     */
    public static void main(String[] args) {
        config = texteditor.config.EditorConfigLoader.load();
        openNewWindow();
    }

    /** Opens a brand-new editor window. */
    public static void openNewWindow() {
        EditorFrame frame = new EditorFrame(config);
        frame.setVisible(true);
    }

    /**
     * Creates a single editor window from the given configuration.
     *
     * @param cfg the loaded editor configuration
     */
    private EditorFrame(EditorConfig cfg) {
        super(cfg.getTitle());
        this.config = cfg;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Build the editing area and apply the YAML visual defaults.
        this.editorPane = new EditorPane(cfg.getFontFamily(), cfg.getFontSize(),
                cfg.getFontColor(), cfg.getBackgroundColor(), cfg.getCaretColor(),
                cfg.isWrapLine(), cfg.getTabSize());
        this.editorPane.setSearchHighlightColor(cfg.getHighlightColor());

        JScrollPane scrollPane = new JScrollPane(editorPane);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Window size / position from configuration.
        setSize(cfg.getWidth(), cfg.getHeight());
        setLocation(cfg.getX(), cfg.getY());

        setJMenuBar(buildMenuBar());
        registerOpenFrame(this);

        // Allow the window to be closed (removed from the open-frames list).
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                OPEN_FRAMES.remove(EditorFrame.this);
            }
        });
    }

    /** Builds the File / Search / View / Help menu bar. */
    private JMenuBar buildMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        menuBar.add(buildFileMenu());
        menuBar.add(buildSearchMenu());
        menuBar.add(buildViewMenu());
        menuBar.add(buildHelpMenu());

        return menuBar;
    }

    // ------------------------------------------------------------------
    // File menu
    // ------------------------------------------------------------------

    private JMenu buildFileMenu() {
        JMenu file = new JMenu("File");

        addItem(file, new AbstractAction("New") {
            private static final long serialVersionUID = 1L;
            @Override
            public void actionPerformed(ActionEvent e) {
                openNewWindow();
            }
        }, KeyEvent.VK_N);

        addItem(file, new AbstractAction("Open...") {
            private static final long serialVersionUID = 1L;
            @Override
            public void actionPerformed(ActionEvent e) {
                openFile();
            }
        }, KeyEvent.VK_O);

        addItem(file, new AbstractAction("Save") {
            private static final long serialVersionUID = 1L;
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFile();
            }
        }, KeyEvent.VK_S);

        file.addSeparator();

        addItem(file, new AbstractAction("Print...") {
            private static final long serialVersionUID = 1L;
            @Override
            public void actionPerformed(ActionEvent e) {
                printDocument();
            }
        }, KeyEvent.VK_P);

        file.addSeparator();

        addItem(file, new AbstractAction("Exit") {
            private static final long serialVersionUID = 1L;
            @Override
            public void actionPerformed(ActionEvent e) {
                exitApplication();
            }
        }, KeyEvent.VK_X);

        return file;
    }

    // ------------------------------------------------------------------
    // Search menu
    // ------------------------------------------------------------------

    private JMenu buildSearchMenu() {
        JMenu search = new JMenu("Search");

        addItem(search, new AbstractAction("Find...") {
            private static final long serialVersionUID = 1L;
            @Override
            public void actionPerformed(ActionEvent e) {
                searchInDocument();
            }
        }, KeyEvent.VK_F);

        return search;
    }

    // ------------------------------------------------------------------
    // View menu
    // ------------------------------------------------------------------

    private JMenu buildViewMenu() {
        JMenu view = new JMenu("View");

        // Font size controls reflect the loaded YAML default size.
        view.add(new AbstractAction("Increase Font Size") {
            private static final long serialVersionUID = 1L;
            @Override
            public void actionPerformed(ActionEvent e) {
                changeFontSize(1);
            }
        });
        view.add(new AbstractAction("Decrease Font Size") {
            private static final long serialVersionUID = 1L;
            @Override
            public void actionPerformed(ActionEvent e) {
                changeFontSize(-1);
            }
        });
        view.addSeparator();
        view.add(new AbstractAction("Select All") {
            private static final long serialVersionUID = 1L;
            @Override
            public void actionPerformed(ActionEvent e) {
                editorPane.selectAll();
            }
        });

        return view;
    }

    // ------------------------------------------------------------------
    // Help menu
    // ------------------------------------------------------------------

    private JMenu buildHelpMenu() {
        JMenu help = new JMenu("Help");

        addItem(help, new AbstractAction("Time & Date") {
            private static final long serialVersionUID = 1L;
            @Override
            public void actionPerformed(ActionEvent e) {
                insertTimeAndDate();
            }
        }, KeyEvent.VK_T);

        help.addSeparator();

        help.add(new AbstractAction("About") {
            private static final long serialVersionUID = 1L;
            @Override
            public void actionPerformed(ActionEvent e) {
                showAbout();
            }
        });

        return help;
    }

    // ------------------------------------------------------------------
    // Menu helpers
    // ------------------------------------------------------------------

    /** Adds a menu item to the menu and optionally binds a shortcut key. */
    private void addItem(JMenu menu, AbstractAction action, int keyCode) {
        JMenuItem item = new JMenuItem(action);
        if (keyCode != -1) {
            item.setAccelerator(KeyStroke.getKeyStroke(keyCode,
                    Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        }
        menu.add(item);
    }

    // ------------------------------------------------------------------
    // Function implementations
    // ------------------------------------------------------------------

    /** New &amp; Open helper: opens a file through the standard file chooser. */
    private void openFile() {
        JFileChooser chooser = new JFileChooser(initialDirectory());
        chooser.setDialogTitle("Open Text File");
        chooser.setFileFilter(new FileNameExtensionFilter(
                "Text files (*.txt)", "txt"));

        int result = chooser.showOpenDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) {
            return; // user cancelled
        }

        File file = chooser.getSelectedFile();
        try {
            DocumentLoader loader = DocumentLoaderRegistry.getInstance().findLoader(file);
            if (loader == null) {
                JOptionPane.showMessageDialog(this,
                        "No loader is available for this file type: "
                                + file.getName(),
                        "Cannot Open", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String content = loader.load(file);
            editorPane.setText(content);
            currentFile = file;
            setTitle(config.getTitle() + " - " + file.getName());
        } catch (IOException ex) {
            showError("Failed to open the file.", ex);
        }
    }

    /** Save: writes the document to a .txt file chosen by the user. */
    private void saveFile() {
        JFileChooser chooser = new JFileChooser(initialDirectory());
        chooser.setDialogTitle("Save Text File");
        chooser.setFileFilter(new FileNameExtensionFilter(
                "Text files (*.txt)", "txt"));

        // Pre-select the current file (if any) to avoid retyping its name.
        if (currentFile != null) {
            chooser.setSelectedFile(currentFile);
        } else {
            chooser.setSelectedFile(new File("untitled.txt"));
        }

        int result = chooser.showSaveDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) {
            return; // user cancelled
        }

        File file = chooser.getSelectedFile();
        // Ensure the .txt extension is present.
        if (!file.getName().toLowerCase().endsWith(".txt")) {
            file = new File(file.getParentFile(), file.getName() + ".txt");
        }

        try {
            TextFileWriter.save(file, editorPane.getText());
            currentFile = file;
            setTitle(config.getTitle() + " - " + file.getName());
        } catch (IOException ex) {
            showError("Failed to save the file.", ex);
        }
    }

    /** Search: asks the user for a word and highlights every match. */
    private void searchInDocument() {
        String query = JOptionPane.showInputDialog(this, "Find what:", "Search");
        if (query == null) {
            return; // cancelled
        }
        boolean found = editorPane.search(query, config.isCaseSensitive());
        if (!found) {
            JOptionPane.showMessageDialog(this,
                    "No matches found for \"" + query + "\".",
                    "Search", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /** Time &amp; Date: inserts the current date and time at the top of the page. */
    private void insertTimeAndDate() {
        String stamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        editorPane.insertAtTop(stamp + System.lineSeparator());
    }

    /** Print: sends the document to the local printer. */
    private void printDocument() {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable((graphics, pageFormat, pageIndex) -> {
            if (pageIndex > 0) {
                return java.awt.print.Printable.NO_SUCH_PAGE;
            }
            // Print the whole text, wrapped to the printable width.
            java.awt.Graphics2D g2 = (java.awt.Graphics2D) graphics;
            g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
            String text = editorPane.getText();
            int y = 0;
            int lineHeight = g2.getFontMetrics().getHeight();
            for (String line : text.split("\n", -1)) {
                String[] pieces = wrapLine(line, g2,
                        (int) pageFormat.getImageableWidth());
                for (String piece : pieces) {
                    g2.drawString(piece, 0, y);
                    y += lineHeight;
                    if (y > pageFormat.getImageableHeight()) {
                        break;
                    }
                }
                if (y > pageFormat.getImageableHeight()) {
                    break;
                }
            }
            return java.awt.print.Printable.PAGE_EXISTS;
        });

        boolean doPrint = job.printDialog();
        if (!doPrint) {
            return; // user cancelled
        }
        try {
            job.print();
        } catch (PrinterException ex) {
            showError("Printing failed.", ex);
        }
    }

    /** Wraps a line into pieces that fit the given width (for printing). */
    private static String[] wrapLine(String line, java.awt.Graphics2D g2, int width) {
        java.util.List<String> pieces = new java.util.ArrayList<>();
        java.awt.FontMetrics fm = g2.getFontMetrics();
        if (fm.stringWidth(line) <= width) {
            return new String[]{line};
        }
        StringBuilder cur = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            cur.append(c);
            if (fm.stringWidth(cur.toString()) > width) {
                cur.deleteCharAt(cur.length() - 1);
                pieces.add(cur.toString());
                cur.setLength(0);
                cur.append(c);
            }
        }
        if (cur.length() > 0) {
            pieces.add(cur.toString());
        }
        return pieces.toArray(new String[0]);
    }

    /** About: shows both group members and a brief message. */
    private void showAbout() {
        String message = config.getGroup() + "\n"
                + "251.251 Software Design and Construction - Assignment 1\n"
                + "Version " + config.getVersion() + "\n"
                + "A standard text editor written in Java.";
        JOptionPane.showMessageDialog(this, message, config.getAboutTitle(),
                JOptionPane.INFORMATION_MESSAGE);
    }

    /** Exit: closes every open window and terminates the program. */
    private void exitApplication() {
        for (EditorFrame frame : new ArrayList<>(OPEN_FRAMES)) {
            frame.dispose();
        }
        System.exit(0);
    }

    /** Increases / decreases the font size of the editing area. */
    private void changeFontSize(int delta) {
        java.awt.Font font = editorPane.getFont();
        int newSize = Math.max(8, font.getSize() + delta);
        editorPane.setFont(font.deriveFont((float) newSize));
    }

    // ------------------------------------------------------------------
    // Small helpers
    // ------------------------------------------------------------------

    /** Returns the folder to start browsing from (from YAML or home dir). */
    private File initialDirectory() {
        if (config.getDefaultFolder() != null && !config.getDefaultFolder().isEmpty()) {
            File f = new File(config.getDefaultFolder());
            if (f.isDirectory()) {
                return f;
            }
        }
        return new File(System.getProperty("user.home"));
    }

    /** Registers a window in the static open-frames list. */
    private static void registerOpenFrame(EditorFrame frame) {
        synchronized (OPEN_FRAMES) {
            OPEN_FRAMES.add(frame);
        }
    }

    /** Shows an error dialog together with the exception detail. */
    private void showError(String message, Exception ex) {
        JOptionPane.showMessageDialog(this, message + "\n" + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        System.err.println(message + ": " + ex.getMessage());
    }
}
