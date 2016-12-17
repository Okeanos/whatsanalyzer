package com.okeanos.whatsanalyzer.person;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
    private static final List<List<String>> RAW_MESSAGES_PERSON_A_MONTHLY = new ArrayList<>();
    private static final List<List<String>> MESSAGES_PERSON_A_MONTHLY = new ArrayList<>();
    private static final List<Integer> MESSAGES_PERSON_A_MONTHLY_COUNT = new ArrayList<>();
    private static final List<Integer> WORDS_PERSON_A_MONTHLY_COUNT = new ArrayList<>();
    private static final List<Integer> SENTENCES_PERSON_A_MONTHLY_COUNT = new ArrayList<>();

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

        initPersonA();
    }

    private static void initPersonA() {
        IntStream.range(0, 12)
            .forEach(i -> {
                MESSAGES_PERSON_A_MONTHLY.add(new ArrayList<>());
                RAW_MESSAGES_PERSON_A_MONTHLY.add(new ArrayList<>());
                MESSAGES_PERSON_A_MONTHLY_COUNT.add(0);
                WORDS_PERSON_A_MONTHLY_COUNT.add(0);
                SENTENCES_PERSON_A_MONTHLY_COUNT.add(0);
            });

        RAW_MESSAGES_PERSON_A.add("27/01/2015, 21:58:27: Okeanos: Lorem ipsum dolor sit amet, consectetur adipiscing "
                                  + "elit.");
        RAW_MESSAGES_PERSON_A.add("27/02/2015, 21:59:55: Okeanos: Nulla facilisis, dui vitae tempus dignissim, lorem "
                                  + "diam "
                                  + "dictum eros.");
        RAW_MESSAGES_PERSON_A.add("28/04/2015, 09:15:16: Okeanos: Mauris quis diam vestibulum, aliquet magna at, "
                                  + "porta turpis.");
        RAW_MESSAGES_PERSON_A.add("27/01/2016, 21:58:27: Okeanos: Lorem ipsum dolor sit amet, consectetur adipiscing "
                                  + "elit.");
        RAW_MESSAGES_PERSON_A.add("27/02/2016, 21:59:55: Okeanos: Nulla facilisis, dui vitae tempus dignissim, lorem "
                                  + "diam "
                                  + "dictum eros.");
        RAW_MESSAGES_PERSON_A.add("28/04/2016, 09:15:16: Okeanos: Mauris quis diam vestibulum, aliquet magna at, "
                                  + "porta turpis.");
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
            equalTo(WORDS_PERSON_A_MONTHLY_COUNT.stream().reduce(0, (a, b) -> a + b)));
        assertThat("Total sentence count is not correct", person.getTotalSentenceCount(),
            equalTo(SENTENCES_PERSON_A_MONTHLY_COUNT.stream().reduce(0, (a, b) -> a + b)));
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
    @Ignore
    public void testNullInHistoryItems() {
        // This test is missing
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
        final List<Integer> emptyListInteger = new ArrayList<>();
        final List<List<String>> emptyListOfLists = new ArrayList<>();

        IntStream.range(0, 12).forEach(i -> {
            emptyListInteger.add(0);
            emptyListOfLists.add(new ArrayList<>());
        });

        assertThat("Name is not correct", person.getName(), equalTo(name));
        assertThat("Raw history is not correct", person.getRawHistory(), equalTo(new ArrayList<>()));
        assertThat("Raw monthly history is not correct", person.getMonthlyRawHistory(),
            equalTo(emptyListOfLists));
        assertThat("Monthly history is not correct", person.getMonthlyHistory(),
            equalTo(emptyListOfLists));
        assertThat("Monthly message count is not correct", person.getMonthlyMessageCounts(),
            equalTo(emptyListInteger));
        assertThat("Monthly word count is not correct", person.getMonthlyWordCounts(),
            equalTo(emptyListInteger));
        assertThat("Monthly sentence count is not correct", person.getMonthlySentenceCounts(),
            equalTo(emptyListInteger));
        assertThat("Total message count is not correct", person.getTotalMessageCount(),
            equalTo(0));
        assertThat("Total word count is not correct", person.getTotalWordCount(),
            equalTo(0));
        assertThat("Total sentence count is not correct", person.getTotalSentenceCount(),
            equalTo(0));
    }
}
