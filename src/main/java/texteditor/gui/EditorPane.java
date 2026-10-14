package texteditor.gui;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.Color;
import java.awt.Font;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The editing area of the text editor.
 *
 * <p>This component wraps a {@link JTextArea} and adds editor specific
 * helpers on top of the plain text area:</p>
 * <ul>
 *   <li>applying the visual defaults read from the YAML configuration
 *       (font family, size, foreground/background and caret colours);</li>
 *   <li>searching for a word inside the document and highlighting every
 *       match (the <em>Search</em> function of the editor);</li>
 *   <li>inserting text at the top of the page (used by Time &amp; Date).</li>
 * </ul>
 *
 * <p>Written by <b>Member A</b> as part of the GUI and basic editing modules.</p>
 *
 * @author Bi Tongcheng (25014402)
 * @version 1.0.0
 */
public class EditorPane extends JTextArea {

    private static final long serialVersionUID = 1L;

    /** Highlighter tag used for search matches (kept to remove them later). */
    private final Object searchHighlightTag = new Object();

    /** Colour used to highlight the search matches. */
    private Color searchHighlightColor = Color.YELLOW;

    /**
     * Constructs the editor pane.
     *
     * @param fontFamily  the font family name
     * @param fontSize    the font size in points
     * @param foreground  the text (foreground) colour
     * @param background  the background colour
     * @param caretColor  the caret colour
     * @param wrapLine    whether long lines should wrap
     * @param tabSize     the tab size (spaces)
     */
    public EditorPane(String fontFamily, int fontSize, Color foreground,
                      Color background, Color caretColor, boolean wrapLine,
                      int tabSize) {
        setFont(new Font(fontFamily, Font.PLAIN, fontSize));
        setForeground(foreground);
        setBackground(background);
        setCaretColor(caretColor);
        setLineWrap(wrapLine);
        setWrapStyleWord(wrapLine);
        setTabSize(tabSize);
        setEditable(true);
    }

    /** Sets the colour used to highlight search matches. */
    public void setSearchHighlightColor(Color color) {
        this.searchHighlightColor = color;
    }

    /**
     * Searches the document for the given word and highlights every match.
     *
     * <p>An empty or {@code null} query clears the previous highlights and
     * returns {@code false}. When a match is found the caret is moved to the
     * first occurrence. User feedback is handled by the caller (menu action).</p>
     *
     * @param word           the word to search for
     * @param caseSensitive  whether the search is case sensitive
     * @return {@code true} if at least one match was found
     */
    public boolean search(String word, boolean caseSensitive) {
        // Remove any previous search highlights first.
        clearSearchHighlights();

        if (word == null || word.isEmpty()) {
            return false;
        }

        String text = getText();
        Pattern pattern;
        try {
            int flags = caseSensitive ? 0 : Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE;
            pattern = Pattern.compile(Pattern.quote(word), flags);
        } catch (Exception e) {
            // A malformed pattern should never prevent the editor from working.
            return false;
        }

        Matcher matcher = pattern.matcher(text);
        Highlighter highlighter = getHighlighter();
        boolean found = false;
        int firstStart = -1;

        try {
            while (matcher.find()) {
                int start = matcher.start();
                int end = matcher.end();
                highlighter.addHighlight(start, end,
                        new DefaultHighlighter.DefaultHighlightPainter(searchHighlightColor));
                if (!found) {
                    firstStart = start;
                }
                found = true;
            }
        } catch (BadLocationException e) {
            // Highlighting is best-effort; the search still reports its result.
        }

        if (found && firstStart >= 0) {
            setCaretPosition(firstStart);
            moveCaretPosition(firstStart + word.length());
            requestFocusInWindow();
        }
        return found;
    }

    /** Removes all search highlights from the document. */
    public void clearSearchHighlights() {
        getHighlighter().removeAllHighlights();
    }

    /**
     * Inserts the given text at the very top of the document (position 0).
     * Used by the Time &amp; Date function to place the current date and time
     * at the top of the editor page, per the assignment requirement.
     *
     * @param text the text to insert at the top
     */
    public void insertAtTop(String text) {
        select(0, 0);
        replaceSelection(text);
        setCaretPosition(0);
    }
}
