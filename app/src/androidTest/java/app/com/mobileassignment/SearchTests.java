package app.com.mobileassignment;

import android.os.SystemClock;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.widget.ListView;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import app.com.mobileassignment.model.City;
import app.com.mobileassignment.views.MainActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SearchTests {
    private static String existingCityName_multipleMatches;
    private static String invalidCityName;
    private static String existingCityName_onlyOneMatch;
    private static int milisecondsToSleep;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);

    @BeforeClass
    public static void Setup() {
        existingCityName_multipleMatches = "Amsterdam";
        invalidCityName = "InvalidCityName";
        existingCityName_onlyOneMatch = "665 Site Colonia";
        milisecondsToSleep = 10000;
    }

    @Test
    public void searchForValidCityName_multipleMatches() {

        onView(withId(R.id.search))
                .perform(typeText(existingCityName_multipleMatches), closeSoftKeyboard());

        sleep();

        onView(withId(R.id.citiesList))
                .check(matches(withListSize(4)));

        onData(first(withValue(existingCityName_multipleMatches)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void searchForValidCityName_onlyOneMatch() {

        onView(withId(R.id.search))
                .perform(typeText(existingCityName_onlyOneMatch), closeSoftKeyboard());

        sleep();

        onView(withId(R.id.citiesList))
                .check(matches(withListSize(1)));

        onData(first(withValue(existingCityName_onlyOneMatch)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void searchForInvalidCityName() {

        onView(withId(R.id.search))
                .perform(typeText(invalidCityName), closeSoftKeyboard());

        sleep();

        onView(withId(R.id.citiesList))
                .check(matches(withListSize(0)));
    }

    public static <T> Matcher<T> first(final Matcher<T> matcher) {
        return new BaseMatcher<T>() {
            private boolean firstSelected = false;
            @Override
            public boolean matches(final Object item) {
                if (!firstSelected && matcher.matches(item)) {
                    firstSelected = true;
                    return true;
                }
                return false;
            }

            @Override
            public void describeTo(final Description description) {
            }
        };
    }

    public static Matcher<Object> withValue(final String value) {
        return new BoundedMatcher<Object, City>(City.class) {
            @Override public void describeTo(Description description) {
                description.appendText("has value " + value);
            }
            @Override public boolean matchesSafely(City item) {
                return (item.getName().toLowerCase().startsWith(value.toLowerCase()));
            }
        };
    }

    public static Matcher<View> withListSize (final int size) {
        return new TypeSafeMatcher<View>() {
            @Override public boolean matchesSafely (final View view) {
                return ((ListView) view).getCount () == size;
            }

            @Override public void describeTo (final Description description) {
                description.appendText ("ListView should have " + size + " items");
            }
        };
    }

    private void sleep(){
        SystemClock.sleep(milisecondsToSleep);
    }
}
