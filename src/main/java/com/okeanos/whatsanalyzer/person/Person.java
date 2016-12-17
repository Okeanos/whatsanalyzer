package com.okeanos.whatsanalyzer.person;

import cue.lang.Counter;
import cue.lang.SentenceIterator;
import cue.lang.WordIterator;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The person class analyzing the chat history for statistics.
 */
public class Person {

    /**
     * the logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Person.class);
    /**
     * number of months.
     */
    private static final int NUM_OF_MONTHS = 12;

    /**
     * The persons name in the WhatsApp history.
     */
    private final String name;
    /**
     * The full chat history as list of strings, one line per message.
     */
    private final List<String> rawHistory;
    /**
     * The chat history split into months, one list of strings per month.
     */
    private final List<List<String>> monthlyRawHistory = new ArrayList<>(NUM_OF_MONTHS);
    /**
     * The chat history split into months, one list of strings per month with date, time: author: stripped.
     */
    private final List<List<String>> monthlyHistory = new ArrayList<>(NUM_OF_MONTHS);
    /**
     * Number of messages a person sent per month.
     */
    private final List<Integer> monthlyMessageCounts = new ArrayList<>(NUM_OF_MONTHS);
    /**
     * Number of words written per month.
     */
    private final List<Integer> monthlyWordCounts = new ArrayList<>(NUM_OF_MONTHS);
    /**
     * Number of sentences written per month.
     */
    private final List<Integer> monthlySentenceCounts = new ArrayList<>(NUM_OF_MONTHS);

    /**
     * Create statistics for the person in the history.
     *
     * @param name    name to build the statistics for
     * @param history the history to parse
     */
    public Person(final String name, final List<String> history) {
        this.name = validateName(name);
        rawHistory = validateHistory(history).stream().
            filter(l -> l.matches("^[\\d:/, ]+" + name + ": .*"))
            .collect(Collectors.toList());

        IntStream.range(0, NUM_OF_MONTHS)
            .forEach(i -> {
                final String month = StringUtils.leftPad(Integer.toString(i + 1), 2, '0');
                monthlyRawHistory.add(rawHistory.stream()
                    .filter(line -> getPatternForMonth(month).matcher(line).find())
                    .map(l -> l.replace("<\u200Eimage omitted>", "").trim())
                    .collect(Collectors.toList()));
                monthlyHistory.add(monthlyRawHistory.get(i).parallelStream()
                    .map(line -> getPatternForStripping().matcher(line).replaceAll(""))
                    .collect(Collectors.toList()));
                monthlyMessageCounts.add(monthlyRawHistory.get(i).size());
                // count words based on unicode \W non word character splits
                /*
                monthlyWordCounts.add(monthlyRawHistory.get(i).stream()
                        .map(line -> getPatternForStripping().matcher(line).replaceAll(""))
                        .map(line -> Pattern.compile("\\W+", UNICODE_CHARACTER_CLASS).split(line))
                        .map(words -> words.length)
                        .reduce(0, (a, b) -> a + b));
                */
                // cue.language word counting
                final Counter<String> words = new Counter<>();
                new WordIterator(monthlyHistory.get(i).parallelStream()
                    .collect(Collectors.joining()))
                    .forEach(words::note);
                monthlyWordCounts.add(words.getTotalItemCount());
                // count sentences
                final Counter<String> sentences = new Counter<>();
                new SentenceIterator(monthlyHistory.get(i).parallelStream()
                    .collect(Collectors.joining()), Locale.GERMAN)
                    .forEach(sentences::note);
                monthlySentenceCounts.add(sentences.getTotalItemCount());
            });

        IntStream.range(0, monthlyRawHistory.size())
            .forEach(i -> LOGGER.debug(MarkerFactory.getMarker("47de10f5-5830-4cc5-ba8d-331b8093b743"),
                (i + 1) + ": " + monthlyRawHistory.get(i)));
    }

    /**
     * Pattern to split the history into month long lists.
     *
     * @param month the month as leftpadded string (01 to 12)
     * @return the pattern
     */
    private Pattern getPatternForMonth(final String month) {
        return Pattern.compile("^\\d\\d/" + month + "/\\d{4}[:, \\d]+" + name + ": .*");
    }

    /**
     * Pattern to strip the "Date, Time: Author: " part from the messages.
     *
     * @return the pattern
     */
    private Pattern getPatternForStripping() {
        return Pattern.compile("^[\\d:/, ]+" + name + ": ");
    }

    /**
     * Checks whether the given name is valid, i.e. not empty
     *
     * @param name the name to check
     * @return the checked name
     */
    private String validateName(final String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidParameterException("The name may not be empty.");
        }

        return name.trim();
    }

    /**
     * Checks whether the given history is valid, i.e. not empty
     *
     * @param history the name to check
     * @return the checked history
     */
    private List<String> validateHistory(final List<String> history) {
        if (history == null || history.isEmpty()) {
            throw new InvalidParameterException("The history may not be empty.");
        }

        return history;
    }

    /**
     * The name of the person.
     *
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * The raw history.
     *
     * @return the history.
     */
    public List<String> getRawHistory() {
        return rawHistory;
    }

    /**
     * The monthly raw history.
     *
     * @return the monthly history.
     */
    public List<List<String>> getMonthlyRawHistory() {
        return monthlyRawHistory;
    }

    /**
     * The monthly history stripped of the date, time: author: prefix.
     *
     * @return the monthly history.
     */
    public List<List<String>> getMonthlyHistory() {
        return monthlyHistory;
    }

    /**
     * The monthly message counts.
     *
     * @return the counts.
     */
    public List<Integer> getMonthlyMessageCounts() {
        return monthlyMessageCounts;
    }

    /**
     * The monthly word counts.
     *
     * @return the counts.
     */
    public List<Integer> getMonthlyWordCounts() {
        return monthlyWordCounts;
    }

    /**
     * The monthly sentence counts.
     *
     * @return the counts.
     */
    public List<Integer> getMonthlySentenceCounts() {
        return monthlySentenceCounts;
    }

    /**
     * The total number of messages.
     *
     * @return the count.
     */
    public int getTotalMessageCount() {
        return monthlyMessageCounts.parallelStream().reduce(0, (a, b) -> a + b);
    }

    /**
     * The total number of words.
     *
     * @return the count.
     */
    public int getTotalWordCount() {
        return monthlyWordCounts.parallelStream().reduce(0, (a, b) -> a + b);
    }

    /**
     * The total number of sentences.
     *
     * @return the count.
     */
    public int getTotalSentenceCount() {
        return monthlySentenceCounts.parallelStream().reduce(0, (a, b) -> a + b);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("name", name)
            .append("totalMessageCount", getTotalMessageCount())
            .append("totalWordCount", getTotalWordCount())
            .append("totalSentenceCount", getTotalSentenceCount())
            .append("monthlyMessageCounts", listToString(monthlyMessageCounts))
            .append("monthlyWordCounts", listToString(monthlyWordCounts))
            .append("monthlySentenceCounts", listToString(monthlySentenceCounts))
            .toString();
    }

    private String listToString(final List<?> list) {
        return "[" + list.parallelStream()
            .map(Object::toString)
            .collect(Collectors.joining(", ")) + "]";
    }
}
