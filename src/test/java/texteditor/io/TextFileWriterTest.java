package texteditor.io;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the <em>save</em> functionality (writing text files).
 *
 * <p>These are the bonus JUnit tests written by <b>Member A</b> for the
 * {@code save()} behaviour, as required by the assignment.</p>
 *
 * @author Bi Tongcheng (25014402)
 * @version 1.0.0
 */
class TextFileWriterTest {

    @TempDir
    Path tempDir;

    /** Saving writes the exact text content to the file. */
    @Test
    void saveWritesContent() throws IOException {
        File out = tempDir.resolve("out.txt").toFile();
        TextFileWriter.save(out, "line one\nline two");
        assertEquals("line one\nline two", Files.readString(out.toPath()));
    }

    /** Saving with a null file must fail fast. */
    @Test
    void saveRejectsNullFile() {
        assertThrows(IllegalArgumentException.class, () -> TextFileWriter.save(null, "text"));
    }

    /** Saving with a null text must fail fast. */
    @Test
    void saveRejectsNullText() throws IOException {
        File out = tempDir.resolve("out2.txt").toFile();
        assertThrows(IllegalArgumentException.class, () -> TextFileWriter.save(out, null));
    }

    /** Saving into a non-writable location must throw IOException. */
    @Test
    void saveToMissingDirectoryThrows() {
        File bad = tempDir.resolve("no-such-dir/file.txt").toFile();
        assertThrows(IOException.class, () -> TextFileWriter.save(bad, "x"));
    }
}
