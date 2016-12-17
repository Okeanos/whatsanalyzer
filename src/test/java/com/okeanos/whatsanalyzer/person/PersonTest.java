package com.okeanos.whatsanalyzer.person;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * PersonTest.
 */
public class PersonTest {

    private static final String PERSON_A = "Okeanos";
    //private static final String PERSON_B = "Hyperion";
    private static final String PERSON_C = "Phoibe";
    private static final List<String> RAW_MESSAGES = new ArrayList<>();
    private static final List<String> RAW_MESSAGES_PERSON_A = new ArrayList<>();
    private static final List<List<String>> RAW_MESSAGES_PERSON_A_MONTHLY = new ArrayList<>();
    private static final List<List<String>> MESSAGES_PERSON_A_MONTHLY = new ArrayList<>();
    private static final List<Integer> MESSAGES_PERSON_A_MONTHLY_COUNT = new ArrayList<>();
    private static final List<Integer> WORDS_PERSON_A_MONTHLY_COUNT = new ArrayList<>();
    private static final List<Integer> SENTENCES_PERSON_A_MONTHLY_COUNT = new ArrayList<>();
    private static final List<String> RAW_MESSAGES_PERSON_B = new ArrayList<>();

    @BeforeClass
    public static void initTest() {
        RAW_MESSAGES.add("27/01/2016, 21:58:27: Okeanos: Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
                         + "Phasellus pretium, odio sed gravida finibus, felis ipsum eleifend felis, "
                         + "sit amet consequat elit enim sed libero.");
        RAW_MESSAGES.add("27/01/2016, 21:59:18: Hyperion: Pellentesque a justo id ipsum elementum vestibulum nec "
                         + "non nibh. Pellentesque auctor quis nunc quis porta. Fusce eu aliquam lorem.");
        RAW_MESSAGES.add("27/02/2016, 21:59:55: Okeanos: Nulla facilisis, dui vitae tempus dignissim, lorem diam "
                         + "dictum eros, id pharetra felis justo et ante. Nam fermentum mauris in mi iaculis, "
                         + "sed dictum ligula porta.");
        RAW_MESSAGES.add("27/02/2016, 22:33:52: Hyperion: Proin in elit porta, mollis nulla sed, congue nisl. "
                         + "Sed fringilla luctus lorem. Morbi nec feugiat nisi.");
        RAW_MESSAGES.add("28/04/2016, 09:14:11: Hyperion: Etiam massa orci, gravida ut nunc nec, hendrerit mollis "
                         + "ante. Maecenas augue urna, fringilla ut lorem eget, volutpat dapibus tortor.");
        RAW_MESSAGES.add("28/04/2016, 09:15:16: Okeanos: Mauris quis diam vestibulum, aliquet magna at, porta turpis. "
                         + "Phasellus dictum nibh eget nulla commodo dignissim. "
                         + "Vestibulum gravida viverra ligula, vitae condimentum urna viverra vitae.");
        RAW_MESSAGES.add("28/08/2016, 11:57:37: Hyperion: Suspendisse quis ante enim. Maecenas imperdiet diam nulla.");
        RAW_MESSAGES.add("28/08/2016, 13:57:37: Hyperion: <\u200Eimage omitted>");

        initPersonA();
        initPersonB();
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

        RAW_MESSAGES_PERSON_A.add("27/01/2016, 21:58:27: Okeanos: Lorem ipsum dolor sit amet, consectetur adipiscing "
                                  + "elit. Phasellus pretium, odio sed gravida finibus, felis ipsum eleifend felis, "
                                  + "sit amet consequat elit enim sed libero.");
        RAW_MESSAGES_PERSON_A.add("27/02/2016, 21:59:55: Okeanos: Nulla facilisis, dui vitae tempus dignissim, lorem "
                                  + "diam dictum eros, id pharetra felis justo et ante. Nam fermentum mauris in mi "
                                  + "iaculis, sed dictum ligula porta.");
        RAW_MESSAGES_PERSON_A.add("28/04/2016, 09:15:16: Okeanos: Mauris quis diam vestibulum, aliquet magna at, porta "
                                  + "turpis. Phasellus dictum nibh eget nulla commodo dignissim. Vestibulum gravida "
                                  + "viverra ligula, vitae condimentum urna viverra vitae.");

        RAW_MESSAGES_PERSON_A_MONTHLY.get(0).add("27/01/2016, 21:58:27: Okeanos: Lorem ipsum dolor sit amet, "
                                                 + "consectetur adipiscing elit. Phasellus pretium, odio sed gravida "
                                                 + "finibus, felis ipsum eleifend felis, sit amet consequat elit enim "
                                                 + "sed libero.");
        RAW_MESSAGES_PERSON_A_MONTHLY.get(1).add("27/02/2016, 21:59:55: Okeanos: Nulla facilisis, dui vitae tempus "
                                                 + "dignissim, lorem diam dictum eros, id pharetra felis justo et "
                                                 + "ante. Nam fermentum mauris in mi iaculis, sed dictum ligula "
                                                 + "porta.");
        RAW_MESSAGES_PERSON_A_MONTHLY.get(3).add("28/04/2016, 09:15:16: Okeanos: Mauris quis diam vestibulum, aliquet "
                                                 + "magna at, porta turpis. Phasellus dictum nibh eget nulla commodo "
                                                 + "dignissim. Vestibulum gravida viverra ligula, vitae condimentum "
                                                 + "urna viverra vitae.");

        MESSAGES_PERSON_A_MONTHLY_COUNT.set(0, 1);
        MESSAGES_PERSON_A_MONTHLY_COUNT.set(1, 1);
        MESSAGES_PERSON_A_MONTHLY_COUNT.set(3, 1);

        WORDS_PERSON_A_MONTHLY_COUNT.set(0, 25);
        WORDS_PERSON_A_MONTHLY_COUNT.set(1, 26);
        WORDS_PERSON_A_MONTHLY_COUNT.set(3, 25);

        SENTENCES_PERSON_A_MONTHLY_COUNT.set(0, 2);
        SENTENCES_PERSON_A_MONTHLY_COUNT.set(1, 2);
        SENTENCES_PERSON_A_MONTHLY_COUNT.set(3, 3);

        MESSAGES_PERSON_A_MONTHLY.get(0).add("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus "
                                             + "pretium, odio sed gravida finibus, felis ipsum eleifend felis, sit "
                                             + "amet consequat elit enim sed libero.");
        MESSAGES_PERSON_A_MONTHLY.get(1).add("Nulla facilisis, dui vitae tempus dignissim, lorem diam dictum eros, "
                                             + "id pharetra felis justo et ante. Nam fermentum mauris in mi iaculis, "
                                             + "sed dictum ligula porta.");
        MESSAGES_PERSON_A_MONTHLY.get(3).add("Mauris quis diam vestibulum, aliquet magna at, porta turpis. Phasellus "
                                             + "dictum nibh eget nulla commodo dignissim. Vestibulum gravida viverra "
                                             + "ligula, vitae condimentum urna viverra vitae.");
    }

    private static void initPersonB() {
        RAW_MESSAGES_PERSON_B.add("27/01/2016, 21:59:18: Hyperion: Pellentesque a justo id ipsum elementum vestibulum "
                                  + "nec non nibh. Pellentesque auctor quis nunc quis porta. Fusce eu aliquam lorem.");
        RAW_MESSAGES_PERSON_B.add("27/02/2016, 22:33:52: Hyperion: Proin in elit porta, mollis nulla sed, congue nisl. "
                                  + "Sed fringilla luctus lorem. Morbi nec feugiat nisi.");
        RAW_MESSAGES_PERSON_B.add("28/04/2016, 09:14:11: Hyperion: Etiam massa orci, gravida ut nunc nec, hendrerit "
                                  + "mollis ante. Maecenas augue urna, fringilla ut lorem eget, "
                                  + "volutpat dapibus tortor.");
        RAW_MESSAGES_PERSON_B.add("28/08/2016, 11:57:37: Hyperion: Suspendisse quis ante enim. Maecenas imperdiet "
                                  + "diam nulla.");
        RAW_MESSAGES_PERSON_B.add("28/08/2016, 13:57:37: Hyperion: <\u200Eimage omitted>");
    }

    @Test
    public void testHappyCase() {
        final Person person = new Person(PERSON_A, RAW_MESSAGES);

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

        assertEmptyValues(PERSON_A, new Person(PERSON_A, emptyList));
    }

    @Test
    public void testMissingPerson() {
        assertEmptyValues(PERSON_C, new Person(PERSON_C, RAW_MESSAGES));
    }

    @Test(expected = InvalidParameterException.class)
    public void testEmptyHistory() {
        new Person(PERSON_A, new ArrayList<>());
    }

    @Test(expected = InvalidParameterException.class)
    public void testNullHistory() {
        new Person(PERSON_A, null);
    }

    @Test
    @Ignore
    public void testNullInHistoryItems() {
        // This test is missing
    }

    @Test(expected = InvalidParameterException.class)
    public void testEmptyName() {
        new Person("", RAW_MESSAGES);
    }

    @Test(expected = InvalidParameterException.class)
    public void testNullName() {
        new Person(null, RAW_MESSAGES);
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
