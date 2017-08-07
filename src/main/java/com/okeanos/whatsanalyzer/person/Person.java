package com.okeanos.whatsanalyzer.person;

import cue.lang.Counter;
import cue.lang.SentenceIterator;
import cue.lang.WordIterator;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The person class analyzing the chat history for statistics.
 */
public class Person {

    /*
     * the logger.
     */
    //private static final Logger LOGGER = LoggerFactory.getLogger(Person.class);
    /**
     * number of months.
     */
    private static final int NUM_OF_MONTHS = 12;

    /**
     * The persons name in the WhatsApp history.
     */
    private final String name;
    /**
     * The years to consider in the history.
     */
    private final List<String> years;
    /**
     * The full chat history as list of strings, one line per message.
     */
    private final List<String> rawHistory;
    /**
     * The chat history split into years, months, one list of strings per year-month.
     */
    private final Map<String, List<String>> monthlyRawHistory = new TreeMap<>();
    /**
     * The chat history split into years, months, one list of strings per year-month with date, time: author: stripped.
     */
    private final Map<String, List<String>> monthlyHistory = new TreeMap<>();
    /**
     * Number of messages a person sent per year-month.
     */
    private final Map<String, Integer> monthlyMessageCounts = new TreeMap<>();
    /**
     * Number of words written per year-month.
     */
    private final Map<String, Integer> monthlyWordCounts = new TreeMap<>();
    /**
     * Number of sentences written per year-month.
     */
    private final Map<String, Integer> monthlySentenceCounts = new TreeMap<>();
    /**
     * The Year pattern in a WhatsApp message.
     */
    private static final Pattern YEAR_PATTERN = Pattern.compile("^[\\d]{4}$", Pattern.UNICODE_CHARACTER_CLASS);

    /**
     * Create statistics for the person in the history.
     *
     * @param name    name to build the statistics for
     * @param years   the years to parse in the history
     * @param history the history to parse
     * @param locale  the locale to use when counting sentences
     */
    public Person(final String name, final List<String> years, final List<String> history, final Locale locale) {
        this.name = validateName(name);
        this.years = validateYears(years);

        Pattern personLinePattern = getPatternLine();

        rawHistory = validateHistory(history).stream().
            filter(l -> l != null && personLinePattern.matcher(l).matches())
            .collect(Collectors.toList());

        parseLog(locale);
    }

    private void parseLog(final Locale locale) {
        this.years.forEach(year ->
            IntStream.range(0, NUM_OF_MONTHS)
                .forEach(i -> {
                    // prepare current loop variables
                    final String month = StringUtils.leftPad(Integer.toString(i + 1), 2, '0');
                    final String index = year + "-" + month;
                    final List<String> currentMonth = new ArrayList<>();
                    currentMonth.addAll(rawHistory.stream()
                        .filter(line -> getPatternForMonthAndYear(month, year).matcher(line).find())
                        .map(l -> l.replace("<\u200Eimage omitted>", "").trim())
                        .collect(Collectors.toList()));

                    // extract messages for current loop
                    monthlyRawHistory.put(index, currentMonth);
                    monthlyHistory.put(index, currentMonth.stream()
                        .map(line -> getPatternForStripping().matcher(line).replaceAll(""))
                        .collect(Collectors.toList()));
                    // extract message count for current loop
                    monthlyMessageCounts.put(index, currentMonth.size());
                    // cue.language word counting for current loop
                    final Counter<String> words = new Counter<>();
                    //@TODO Compare with Unicode Patterns using the Unicode compile flag and \b and/or \w patterns.
                    new WordIterator(currentMonth.stream()
                        .collect(Collectors.joining()))
                        .forEach(words::note);
                    monthlyWordCounts.put(index, words.getTotalItemCount());
                    // cue.language count sentences for current loop
                    final Counter<String> sentences = new Counter<>();
                    new SentenceIterator(currentMonth.stream()
                        .collect(Collectors.joining()), locale)
                        .forEach(sentences::note);
                    monthlySentenceCounts.put(index, sentences.getTotalItemCount());
                })
        );
    }

    /**
     * Pattern to split the history into month long lists.
     *
     * @param month the month as leftpadded string (01 to 12)
     * @return the pattern
     */
    private Pattern getPatternForMonthAndYear(final String month, final String year) {
        return Pattern.compile("^\\d\\d/" + month + "/" + year + "[:, \\d]+" + name + ": .*", Pattern.UNICODE_CHARACTER_CLASS);
    }

    /**
     * Pattern to matching the person's lines.
     *
     * @return the pattern
     */
    private Pattern getPatternLine() {
        return Pattern.compile("^[\\d:/, ]+" + name + ": .*", Pattern.UNICODE_CHARACTER_CLASS);
    }

    /**
     * Pattern to strip the "Date, Time: Author: " part from the messages.
     *
     * @return the pattern
     */
    private Pattern getPatternForStripping() {
        return Pattern.compile("^[\\d:/, ]+" + name + ": ", Pattern.UNICODE_CHARACTER_CLASS);
    }

    /**
     * Checks whether the given name is valid, i.e. not empty
     *
     * @param name the name to check
     * @return the checked name
     */
    private String validateName(final String name) {
        if (StringUtils.isBlank(name)) {
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
     * Checks whether the given years is valid, i.e. not empty and only contains years in the yyyy format.
     *
     * @param years the name to check
     * @return the checked years
     */
    private List<String> validateYears(final List<String> years) {
        if (years == null || years.isEmpty()) {
            throw new InvalidParameterException("The years may not be empty.");
        }

        years.forEach(year -> {
            if (year == null || year.length() > 4 || !YEAR_PATTERN.matcher(year).find()) {
                throw new InvalidParameterException("The years may not be empty.");
            }
        });

        return years;
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
     * The years.
     *
     * @return the years
     */
    public List<String> getYears() {
        return years;
    }

    /**
     * The raw history.
     *
     * @return the history
     */
    public List<String> getRawHistory() {
        return rawHistory;
    }

    /**
     * The monthly raw history.
     *
     * @return the monthly history
     */
    public Map<String, List<String>> getMonthlyRawHistory() {
        return monthlyRawHistory;
    }

    /**
     * The monthly history stripped of the date, time: author: prefix.
     *
     * @return the monthly history
     */
    public Map<String, List<String>> getMonthlyHistory() {
        return monthlyHistory;
    }

    /**
     * The monthly message counts.
     *
     * @return the counts
     */
    public Map<String, Integer> getMonthlyMessageCounts() {
        return monthlyMessageCounts;
    }

    /**
     * The monthly word counts.
     *
     * @return the counts
     */
    public Map<String, Integer> getMonthlyWordCounts() {
        return monthlyWordCounts;
    }

    /**
     * The monthly sentence counts.
     *
     * @return the counts
     */
    public Map<String, Integer> getMonthlySentenceCounts() {
        return monthlySentenceCounts;
    }

    /**
     * The total number of messages.
     *
     * @return the count
     */
    public int getTotalMessageCount() {
        return monthlyMessageCounts.values().stream().reduce(0, (a, b) -> a + b);
    }

    /**
     * The total number of words.
     *
     * @return the count
     */
    public int getTotalWordCount() {
        return monthlyWordCounts.values().stream().reduce(0, (a, b) -> a + b);
    }

    /**
     * The total number of sentences.
     *
     * @return the count
     */
    public int getTotalSentenceCount() {
        return monthlySentenceCounts.values().stream().reduce(0, (a, b) -> a + b);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("name", name)
            .append("totalMessageCount", getTotalMessageCount())
            .append("totalWordCount", getTotalWordCount())
            .append("totalSentenceCount", getTotalSentenceCount())
            .append("monthlyMessageCounts", mapToString(monthlyMessageCounts))
            .append("monthlyWordCounts", mapToString(monthlyWordCounts))
            .append("monthlySentenceCounts", mapToString(monthlySentenceCounts))
            .toString();
    }

    private String mapToString(final Map<?, ?> map) {
        final StringBuilder b = new StringBuilder();

        map.forEach((k, v) -> {
            b.append(k);
            b.append(": ");
            b.append(v);
            b.append(", ");
        });

        return "[" + b.toString() + " ]";
    }
}
