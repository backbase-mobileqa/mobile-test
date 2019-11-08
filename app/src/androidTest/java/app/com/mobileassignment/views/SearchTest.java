package app.com.mobileassignment.views;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import app.com.mobileassignment.R;
import app.com.mobileassignment.model.City;

import static android.os.SystemClock.sleep;
import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SearchTest {

    private static final String NON_EXISTING_CITY = "Zainab";

    private static final String LAST_CITY_DISPLAYED = "Lagostelle, ES";

    private static final String KEYWORD = "Lagos";

    private static final String EXISTING_CITY = "zinal";

    private static final String MATCHING_CITY = "Zinal, CH";

    private static final String CITY_FROM_LIST = "A Rua, ES";


    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void searchForANonExistingCityName() {
        onView(withId(R.id.search)).check(matches(isDisplayed()));
        onView(withHint("Search"));
        onView(withId(R.id.search)).perform(replaceText(NON_EXISTING_CITY));
        sleep(12000);
        closeSoftKeyboard();

        onView(withId(R.id.cityName)).check(doesNotExist());
    }

    @Test
    public void searchWithPartialWord() {
        onView(withId(R.id.search)).check(matches(isDisplayed()));
        onView(withHint("Search"));
        onView(withId(R.id.search)).perform(replaceText(KEYWORD));
        sleep(10000);
        closeSoftKeyboard();

        onView(withText(LAST_CITY_DISPLAYED)).check(matches(isCompletelyDisplayed()));

    }

    @Test
    public void searchForAnExistingCity() throws InterruptedException {
        onView(withId(R.id.search)).check(matches(isDisplayed()));
        onView(withHint("Search"));
        onView(withId(R.id.search)).perform(replaceText(EXISTING_CITY));
        Thread.sleep(10_000);
        closeSoftKeyboard();
        onData(instanceOf(City.class))
                .atPosition(0)
                .onChildView(withId(R.id.cityName))
                .check(matches(withText(MATCHING_CITY)));
    }

    @Test
    public void selectFromList() {
        onView(withId(R.id.search)).check(matches(isDisplayed()));
        onView(withHint("Search"));
        onView(withText(CITY_FROM_LIST)).perform(click());
    }
}

