package texteditor.io;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the <em>open</em> functionality (plain text loading).
 *
 * <p>These are the bonus JUnit tests written by <b>Member A</b> for the
 * {@code open()} behaviour, as required by the assignment.</p>
 *
 * @author Bi Tongcheng (25014402)
 * @version 1.0.0
 */
class PlainTextLoaderTest {

    @TempDir
    Path tempDir;

    /** A plain .txt file must be supported. */
    @Test
    void supportsTxtFile() throws IOException {
        File txt = Files.createFile(tempDir.resolve("hello.txt")).toFile();
        assertTrue(new PlainTextLoader().supports(txt));
    }

    /** Non-text files (and non-existent files) are not supported. */
    @Test
    void doesNotSupportOtherExtensions() throws IOException {
        File odt = Files.createFile(tempDir.resolve("doc.odt")).toFile();
        assertFalse(new PlainTextLoader().supports(odt));
    }

    /** Opening a .txt file must return its exact content. */
    @Test
    void openReadsTxtContent() throws IOException {
        Path file = tempDir.resolve("sample.txt");
        Files.writeString(file, "Hello, world!\nSecond line.");
        String content = new PlainTextLoader().load(file.toFile());
        assertEquals("Hello, world!\nSecond line.", content);
    }

    /** Opening a missing file must throw IOException. */
    @Test
    void openMissingFileThrows() {
        File missing = tempDir.resolve("nope.txt").toFile();
        assertThrows(IOException.class, () -> new PlainTextLoader().load(missing));
    }
}
