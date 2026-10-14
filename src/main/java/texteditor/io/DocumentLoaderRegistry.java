package texteditor.io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Registry of all available {@link DocumentLoader} implementations.
 *
 * <p>The GUI's Open menu asks this registry for a loader that supports the
 * selected file, instead of hard-coding the supported formats. This keeps
 * the GUI decoupled from the concrete formats:</p>
 * <ul>
 *   <li><b>Member A</b> registers {@link PlainTextLoader} for {@code .txt}.</li>
 *   <li><b>Member B</b> registers loaders for {@code .odt}, {@code .rtf} and
 *       source-code files (for syntax highlighting) without touching the GUI.</li>
 * </ul>
 *
 * @author Bi Tongcheng (25014402)
 * @version 1.0.0
 */
public final class DocumentLoaderRegistry {

    /** Ordered list of registered loaders (last registered wins on ties). */
    private final List<DocumentLoader> loaders = new ArrayList<>();

    /** Lazily-created shared instance, configured with the built-in loaders. */
    private static volatile DocumentLoaderRegistry instance;

    /** Private constructor: registers the default loaders. */
    private DocumentLoaderRegistry() {
        register(new PlainTextLoader());
    }

    /**
     * Returns the shared registry, initialised with the standard loaders
     * written by member A.
     *
     * @return the shared registry instance
     */
    public static DocumentLoaderRegistry getInstance() {
        if (instance == null) {
            synchronized (DocumentLoaderRegistry.class) {
                if (instance == null) {
                    instance = new DocumentLoaderRegistry();
                }
            }
        }
        return instance;
    }

    /**
     * Adds a new loader to the registry.
     *
     * @param loader the loader to add (must not be {@code null})
     */
    public void register(DocumentLoader loader) {
        if (loader != null) {
            loaders.add(loader);
        }
    }

    /**
     * Finds a loader able to open the given file.
     *
     * @param file the file to open
     * @return the first matching loader, or {@code null} if none supports it
     */
    public DocumentLoader findLoader(File file) {
        for (DocumentLoader loader : loaders) {
            if (loader.supports(file)) {
                return loader;
            }
        }
        return null;
    }

    /**
     * Returns an unmodifiable view of the registered loaders.
     *
     * @return the list of registered loaders
     */
    public List<DocumentLoader> getLoaders() {
        return java.util.Collections.unmodifiableList(loaders);
    }
}
