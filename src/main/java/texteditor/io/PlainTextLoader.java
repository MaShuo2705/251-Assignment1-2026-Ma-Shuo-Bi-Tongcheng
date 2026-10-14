package texteditor.io;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * Loader for plain text files ({@code .txt}).
 *
 * <p>This is the loader written by <b>Member A</b> and it handles the
 * standard text files that form the base functionality of the editor.
 * The file is read with UTF-8 encoding so that ASCII and international
 * characters are both preserved.</p>
 *
 * @author Bi Tongcheng (25014402)
 * @version 1.0.0
 */
public final class PlainTextLoader implements DocumentLoader {

    /** The file extension handled by this loader. */
    private static final String EXTENSION = ".txt";

    /**
     * Returns {@code true} when the file name ends with {@code .txt}
     * (case-insensitive). All other formats are delegated to other loaders.
     */
    @Override
    public boolean supports(File file) {
        if (file == null || !file.isFile()) {
            return false;
        }
        String name = file.getName().toLowerCase();
        return name.endsWith(EXTENSION);
    }

    /**
     * Reads the whole text file as a UTF-8 string.
     *
     * @param file the text file to read
     * @return the file content
     * @throws IOException if the file cannot be read
     */
    @Override
    public String load(File file) throws IOException {
        return new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
    }
}
