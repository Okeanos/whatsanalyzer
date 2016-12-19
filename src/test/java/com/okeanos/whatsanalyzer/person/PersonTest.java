package com.okeanos.whatsanalyzer.person;

import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.security.InvalidParameterException;
import java.util.*;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * PersonTest.
 */
public class PersonTest {

    private static final String PERSON_A = "Okeanos";
    private static final String PERSON_C = "Phoibe";
    private static final Locale LOCALE = Locale.ENGLISH;
    private static final List<String> YEARS = new ArrayList<>();
    private static final List<String> RAW_MESSAGES = new ArrayList<>();
    private static final List<String> RAW_MESSAGES_PERSON_A = new ArrayList<>();
    private static final Map<String, List<String>> RAW_MESSAGES_PERSON_A_MONTHLY = new TreeMap<>();
    private static final Map<String, List<String>> MESSAGES_PERSON_A_MONTHLY = new TreeMap<>();
    private static final Map<String, Integer> MESSAGES_PERSON_A_MONTHLY_COUNT = new TreeMap<>();
    private static final Map<String, Integer> WORDS_PERSON_A_MONTHLY_COUNT = new TreeMap<>();
    private static final Map<String, Integer> SENTENCES_PERSON_A_MONTHLY_COUNT = new TreeMap<>();
    private static final Map<String, Integer> EMPTY_MAP_OF_INTEGERS = new TreeMap<>();
    private static final Map<String, List<String>> EMPTY_MAP_OF_LISTS = new TreeMap<>();

    @BeforeClass
    public static void initTest() {
        RAW_MESSAGES.add("27/01/2015, 21:58:27: Okeanos: Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
        RAW_MESSAGES.add("27/01/2015, 21:59:18: Hyperion: Pellentesque a justo id ipsum elementum vestibulum nec "
                         + "non nibh.");
        RAW_MESSAGES.add("27/02/2015, 21:59:55: Okeanos: Nulla facilisis, dui vitae tempus dignissim, lorem diam "
                         + "dictum eros.");
        RAW_MESSAGES.add("27/02/2015, 22:33:52: Hyperion: Proin in elit porta, mollis nulla sed, congue nisl.");
        RAW_MESSAGES.add("28/04/2015, 09:14:11: Hyperion: Etiam massa orci, gravida ut nunc nec, hendrerit mollis "
                         + "ante.");
        RAW_MESSAGES.add("28/04/2015, 09:15:16: Okeanos: Mauris quis diam vestibulum, aliquet magna at, porta turpis.");
        RAW_MESSAGES.add("28/08/2015, 11:57:37: Hyperion: Suspendisse quis ante enim. Maecenas imperdiet diam nulla.");
        RAW_MESSAGES.add("27/01/2016, 21:58:27: Okeanos: Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
        RAW_MESSAGES.add("27/01/2016, 21:59:18: Hyperion: Pellentesque a justo id ipsum elementum vestibulum nec "
                         + "non nibh.");
        RAW_MESSAGES.add("27/02/2016, 21:59:55: Okeanos: Nulla facilisis, dui vitae tempus dignissim, lorem diam "
                         + "dictum eros.");
        RAW_MESSAGES.add("27/02/2016, 22:33:52: Hyperion: Proin in elit porta, mollis nulla sed, congue nisl.");
        RAW_MESSAGES.add("28/04/2016, 09:14:11: Hyperion: Etiam massa orci, gravida ut nunc nec, hendrerit mollis "
                         + "ante.");
        RAW_MESSAGES.add("28/04/2016, 09:15:16: Okeanos: Mauris quis diam vestibulum, aliquet magna at, porta turpis.");
        RAW_MESSAGES.add("28/08/2016, 11:57:37: Hyperion: Suspendisse quis ante enim. Maecenas imperdiet diam nulla.");
        RAW_MESSAGES.add("28/09/2016, 12:57:37: Hyperion: <\u200Eimage omitted>");

        YEARS.add("2015");
        YEARS.add("2016");

        YEARS.forEach(year -> {
            IntStream.range(0, 12)
                .forEach(i -> {
                    final String month = StringUtils.leftPad(Integer.toString(i + 1), 2, '0');
                    final String index = year + "-" + month;

                    MESSAGES_PERSON_A_MONTHLY.put(index, new ArrayList<>());
                    RAW_MESSAGES_PERSON_A_MONTHLY.put(index, new ArrayList<>());
                    MESSAGES_PERSON_A_MONTHLY_COUNT.put(index, 0);
                    WORDS_PERSON_A_MONTHLY_COUNT.put(index, 0);
                    SENTENCES_PERSON_A_MONTHLY_COUNT.put(index, 0);

                    EMPTY_MAP_OF_INTEGERS.put(index, 0);
                    EMPTY_MAP_OF_LISTS.put(index, new ArrayList<>());
                });
        });

        initPersonA();
    }

    private static void initPersonA() {
        RAW_MESSAGES_PERSON_A.add("27/01/2015, 21:58:27: Okeanos: Lorem ipsum dolor sit amet, consectetur adipiscing "
                                  + "elit.");
        RAW_MESSAGES_PERSON_A.add("27/02/2015, 21:59:55: Okeanos: Nulla facilisis, dui vitae tempus dignissim, lorem "
                                  + "diam dictum eros.");
        RAW_MESSAGES_PERSON_A.add("28/04/2015, 09:15:16: Okeanos: Mauris quis diam vestibulum, aliquet magna at, "
                                  + "porta turpis.");
        RAW_MESSAGES_PERSON_A.add("27/01/2016, 21:58:27: Okeanos: Lorem ipsum dolor sit amet, consectetur adipiscing "
                                  + "elit.");
        RAW_MESSAGES_PERSON_A.add("27/02/2016, 21:59:55: Okeanos: Nulla facilisis, dui vitae tempus dignissim, lorem "
                                  + "diam dictum eros.");
        RAW_MESSAGES_PERSON_A.add("28/04/2016, 09:15:16: Okeanos: Mauris quis diam vestibulum, aliquet magna at, "
                                  + "porta turpis.");

        RAW_MESSAGES_PERSON_A_MONTHLY.put("2015-01",
            Collections.singletonList("27/01/2015, 21:58:27: Okeanos: Lorem ipsum dolor sit amet, consectetur "
                                      + "adipiscing elit."));
        RAW_MESSAGES_PERSON_A_MONTHLY.put("2015-02",
            Collections.singletonList("27/02/2015, 21:59:55: Okeanos: Nulla facilisis, dui vitae tempus dignissim, "
                                      + "lorem diam dictum eros."));
        RAW_MESSAGES_PERSON_A_MONTHLY.put("2015-04",
            Collections.singletonList("28/04/2015, 09:15:16: Okeanos: Mauris quis diam vestibulum, aliquet magna "
                                      + "at, porta turpis."));
        RAW_MESSAGES_PERSON_A_MONTHLY.put("2016-01",
            Collections.singletonList("27/01/2016, 21:58:27: Okeanos: Lorem ipsum dolor sit amet, consectetur "
                          + "adipiscing elit."));
        RAW_MESSAGES_PERSON_A_MONTHLY.put("2016-02",
            Collections.singletonList("27/02/2016, 21:59:55: Okeanos: Nulla facilisis, dui vitae tempus dignissim, "
                          + "lorem diam dictum eros."));
        RAW_MESSAGES_PERSON_A_MONTHLY.put("2016-04",
            Collections.singletonList("28/04/2016, 09:15:16: Okeanos: Mauris quis diam vestibulum, aliquet magna "
                          + "at, porta turpis."));

        MESSAGES_PERSON_A_MONTHLY.put("2015-01",
            Collections.singletonList("Lorem ipsum dolor sit amet, consectetur adipiscing elit."));
        MESSAGES_PERSON_A_MONTHLY.put("2015-02",
            Collections.singletonList("Nulla facilisis, dui vitae tempus dignissim, lorem diam dictum eros."));
        MESSAGES_PERSON_A_MONTHLY.put("2015-04",
            Collections.singletonList("Mauris quis diam vestibulum, aliquet magna at, porta turpis."));
        MESSAGES_PERSON_A_MONTHLY.put("2016-01",
            Collections.singletonList("Lorem ipsum dolor sit amet, consectetur adipiscing elit."));
        MESSAGES_PERSON_A_MONTHLY.put("2016-02",
            Collections.singletonList("Nulla facilisis, dui vitae tempus dignissim, lorem diam dictum eros."));
        MESSAGES_PERSON_A_MONTHLY.put("2016-04",
            Collections.singletonList("Mauris quis diam vestibulum, aliquet magna at, porta turpis."));

        MESSAGES_PERSON_A_MONTHLY_COUNT.put("2015-01", 1);
        MESSAGES_PERSON_A_MONTHLY_COUNT.put("2015-02", 1);
        MESSAGES_PERSON_A_MONTHLY_COUNT.put("2015-04", 1);
        MESSAGES_PERSON_A_MONTHLY_COUNT.put("2016-01", 1);
        MESSAGES_PERSON_A_MONTHLY_COUNT.put("2016-02", 1);
        MESSAGES_PERSON_A_MONTHLY_COUNT.put("2016-04", 1);

        WORDS_PERSON_A_MONTHLY_COUNT.put("2015-01", 11);
        WORDS_PERSON_A_MONTHLY_COUNT.put("2015-02", 13);
        WORDS_PERSON_A_MONTHLY_COUNT.put("2015-04", 12);
        WORDS_PERSON_A_MONTHLY_COUNT.put("2016-01", 11);
        WORDS_PERSON_A_MONTHLY_COUNT.put("2016-02", 13);
        WORDS_PERSON_A_MONTHLY_COUNT.put("2016-04", 12);

        SENTENCES_PERSON_A_MONTHLY_COUNT.put("2015-01", 1);
        SENTENCES_PERSON_A_MONTHLY_COUNT.put("2015-02", 1);
        SENTENCES_PERSON_A_MONTHLY_COUNT.put("2015-04", 1);
        SENTENCES_PERSON_A_MONTHLY_COUNT.put("2016-01", 1);
        SENTENCES_PERSON_A_MONTHLY_COUNT.put("2016-02", 1);
        SENTENCES_PERSON_A_MONTHLY_COUNT.put("2016-04", 1);
    }

    @Test
    public void testHappyCase() {
        final Person person = new Person(PERSON_A, YEARS, RAW_MESSAGES, LOCALE);

        assertThat("Name is not correct", person.getName(), equalTo(PERSON_A));
        assertThat("Raw history is not correct", person.getRawHistory(),
            equalTo(RAW_MESSAGES_PERSON_A));
        assertThat("Raw monthly history is not correct", person.getMonthlyRawHistory(),
            equalTo(RAW_MESSAGES_PERSON_A_MONTHLY));
        assertThat("Monthly history is not correct", person.getMonthlyHistory(),
            equalTo(MESSAGES_PERSON_A_MONTHLY));
        assertThat("Monthly message count is not correct", person.getMonthlyMessageCounts(),
            equalTo(MESSAGES_PERSON_A_MONTHLY_COUNT));
        assertThat("Monthly word count is not correct", person.getMonthlyWordCounts(),
            equalTo(WORDS_PERSON_A_MONTHLY_COUNT));
        assertThat("Monthly sentence count is not correct", person.getMonthlySentenceCounts(),
            equalTo(SENTENCES_PERSON_A_MONTHLY_COUNT));
        assertThat("Total message count is not correct", person.getTotalMessageCount(),
            equalTo(RAW_MESSAGES_PERSON_A.size()));
        assertThat("Total word count is not correct", person.getTotalWordCount(),
            equalTo(WORDS_PERSON_A_MONTHLY_COUNT.values().parallelStream().reduce(0, (a, b) -> a + b)));
        assertThat("Total sentence count is not correct", person.getTotalSentenceCount(),
            equalTo(SENTENCES_PERSON_A_MONTHLY_COUNT.values().parallelStream().reduce(0, (a, b) -> a + b)));
    }

    @Test
    public void testHappyCaseEmptyHistory() {
        final List<String> emptyList = new ArrayList<>();
        emptyList.add("");

        assertEmptyValues(PERSON_A, new Person(PERSON_A, YEARS, emptyList, LOCALE));
    }

    @Test
    public void testMissingPerson() {
        assertEmptyValues(PERSON_C, new Person(PERSON_C, YEARS, RAW_MESSAGES, LOCALE));
    }

    @Test(expected = InvalidParameterException.class)
    public void testEmptyHistory() {
        new Person(PERSON_A, YEARS, new ArrayList<>(), LOCALE);
    }

    @Test(expected = InvalidParameterException.class)
    public void testNullHistory() {
        new Person(PERSON_A, YEARS, null, LOCALE);
    }

    @Test
    public void testHappyCaseNullInHistoryItems() {
        final List<String> emptyList = new ArrayList<>();
        emptyList.add(null);

        assertEmptyValues(PERSON_A, new Person(PERSON_A, YEARS, emptyList, LOCALE));
    }

    @Test(expected = InvalidParameterException.class)
    public void testEmptyName() {
        new Person("", YEARS, RAW_MESSAGES, LOCALE);
    }

    @Test(expected = InvalidParameterException.class)
    public void testNullName() {
        new Person(null, YEARS, RAW_MESSAGES, LOCALE);
    }

    @Test(expected = InvalidParameterException.class)
    public void testEmptyYears() {
        new Person(PERSON_A, new ArrayList<>(), RAW_MESSAGES, LOCALE);
    }

    @Test(expected = InvalidParameterException.class)
    public void testNullYears() {
        new Person(PERSON_A, null, RAW_MESSAGES, LOCALE);
    }

    @Test(expected = InvalidParameterException.class)
    public void testYearsInvalidA() {
        final List<String> years = new ArrayList<>();
        years.add("2015");
        years.add("2016");
        years.add("201a");
        new Person(PERSON_A, years, RAW_MESSAGES, LOCALE);
    }

    @Test(expected = InvalidParameterException.class)
    public void testYearsInvalidB() {
        final List<String> years = new ArrayList<>();
        years.add("2015");
        years.add("2016");
        years.add("2018,");
        new Person(PERSON_A, years, RAW_MESSAGES, LOCALE);
    }

    private void assertEmptyValues(final String name, final Person person) {
        assertThat("Name is not correct", person.getName(), equalTo(name));
        assertThat("Raw history is not correct", person.getRawHistory(), equalTo(new ArrayList<>()));
        assertThat("Raw monthly history is not correct", person.getMonthlyRawHistory(),
            equalTo(EMPTY_MAP_OF_LISTS));
        assertThat("Monthly history is not correct", person.getMonthlyHistory(),
            equalTo(EMPTY_MAP_OF_LISTS));
        assertThat("Monthly message count is not correct", person.getMonthlyMessageCounts(),
            equalTo(EMPTY_MAP_OF_INTEGERS));
        assertThat("Monthly word count is not correct", person.getMonthlyWordCounts(),
            equalTo(EMPTY_MAP_OF_INTEGERS));
        assertThat("Monthly sentence count is not correct", person.getMonthlySentenceCounts(),
            equalTo(EMPTY_MAP_OF_INTEGERS));
        assertThat("Total message count is not correct", person.getTotalMessageCount(),
            equalTo(0));
        assertThat("Total word count is not correct", person.getTotalWordCount(),
            equalTo(0));
        assertThat("Total sentence count is not correct", person.getTotalSentenceCount(),
            equalTo(0));
    }
}
