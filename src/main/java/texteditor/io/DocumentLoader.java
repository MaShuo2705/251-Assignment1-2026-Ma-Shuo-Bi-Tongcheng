package texteditor.io;

import java.io.File;

/**
 * Contract for loading a document from a file into plain text.
 *
 * <p>This interface is the shared alignment point between the two group
 * members for the <em>Open</em> feature:</p>
 * <ul>
 *   <li><b>Member A</b> provides {@link PlainTextLoader} (reads {@code .txt}
 *       files) and builds the Open menu in the GUI, which asks a
 *       {@link DocumentLoaderRegistry} for a suitable loader.</li>
 *   <li><b>Member B</b> implements additional loaders for other formats
 *       (e.g. {@code .odt}, {@code .rtf}, source-code files) that plug into
 *       the same registry, so the GUI itself does not change.</li>
 * </ul>
 *
 * @author Bi Tongcheng (25014402)
 * @version 1.0.0
 */
public interface DocumentLoader {

    /**
     * Returns {@code true} if this loader can handle the given file
     * (normally decided by its extension, e.g. {@code .txt}).
     *
     * @param file the file to test
     * @return {@code true} if the loader can open {@code file}
     */
    boolean supports(File file);

    /**
     * Reads the given file and returns its content as a plain text string.
     *
     * @param file the file to read
     * @return the textual content of the file
     * @throws java.io.IOException if the file cannot be read
     */
    String load(File file) throws java.io.IOException;
}
