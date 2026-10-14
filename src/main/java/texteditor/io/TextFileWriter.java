package texteditor.io;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * Utility class for saving text content to a file.
 *
 * <p>This handles the <em>Save</em> function of the editor: the text is
 * written to a {@code .txt} file selected by the user through a file chooser.
 * The file is saved with UTF-8 encoding.</p>
 *
 * @author Bi Tongcheng (25014402)
 * @version 1.0.0
 */
public final class TextFileWriter {

    /** Private constructor: this is a utility class. */
    private TextFileWriter() {
    }

    /**
     * Writes the given text to the target file, creating it if necessary.
     *
     * @param file the destination file
     * @param text the text content to write
     * @throws IOException if the file cannot be written
     */
    public static void save(File file, String text) throws IOException {
        if (file == null || text == null) {
            throw new IllegalArgumentException("file and text must not be null");
        }
        Files.write(file.toPath(), text.getBytes(StandardCharsets.UTF_8));
    }
}
