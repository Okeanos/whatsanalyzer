package com.okeanos.whatsanalyzer;

import com.okeanos.whatsanalyzer.person.Person;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * The Application entry point.
 */
public final class Main {

    /**
     * The logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    /**
     * private constructor.
     */
    private Main() {

    }

    /**
     * The application entry point.
     *
     * @param args the arguments
     */
    public static void main(final String[] args) {
        if (args.length < 3) {
            throw new InvalidParameterException("You need to pass at least three parameters: "
                                                + "'path to the chat history', 'years', 'participant name 1'");
        }
        final String fileName = args[0];
        final File file = new File(fileName);

        if (!file.exists() || !file.isFile() || !file.canRead()) {
            throw new InvalidParameterException("You need to pass a valid path to a chat history that can be read.");
        }

        final List<String> years = Arrays.asList(args[1].split("\\D+"));
        if (years.isEmpty()) {
            throw new InvalidParameterException("You need to pass at least one year.");
        }

        final Collection<String> people = Arrays.asList(Arrays.copyOfRange(args, 2, args.length));

        final long startTimeInMilliSeconds = System.currentTimeMillis();

        LOGGER.info(MarkerFactory.getMarker("47ae069a-89a1-4b32-9653-c34256e44d17"),
            "Configuration is: file = '{}', years = [{}], people = [{}]", fileName,
            years.parallelStream().map(Object::toString).collect(Collectors.joining(", ")),
            people.parallelStream().map(Object::toString).collect(Collectors.joining(", ")));

        try (InputStream in = new FileInputStream(fileName)) {
            final List<String> history = IOUtils.readLines(in, "utf-8");

            //@TODO add locale to parameters
            people.forEach(person -> LOGGER.info(new Person(person, years, history, Locale.GERMAN).toString()));
        } catch (final Exception e) {
            LOGGER.error(MarkerFactory.getMarker("a8812cdd-65cf-450d-8248-bb339145e510"),
                "Exception when reading the supplied file {} {} {} {}",
                fileName, e.getMessage(), e.getCause(), e.getStackTrace());
        }
        LOGGER.info(MarkerFactory.getMarker("3e5b41e2-a4fe-4005-9299-f295a2636f43"),
            "Execution took {} ms", System.currentTimeMillis() - startTimeInMilliSeconds);
    }
}
