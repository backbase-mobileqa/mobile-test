package app.com.mobileassignment;

import android.content.Context;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import app.com.mobileassignment.views.MainActivity;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertTrue;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class ApplicationTest {

    private static final String STRING_TO_BE_TYPED = "Paris";
    private static final String CHARACTER_TO_BE_TYPED = "b";
    private static final String NON_EXISTING_CITY_TO_BE_TYPED = "dfjhd";

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(
            MainActivity.class);

    @Before
    public void setUp() {
        Context context = InstrumentationRegistry.getTargetContext();
    }

    //Test Cases Automated
    //Launch Application
    @Test
    public void LaunchApp() throws InterruptedException {
        onView(withId(R.id.search)).check(matches(isDisplayed()));
        onView(withId(R.id.citiesList)).check(matches(isDisplayed()));
    }

    //Search with no character
    @Test
    public void searchWithNoCharacter() throws Exception {
        onView(withId(R.id.search))
                .perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.search)).perform(click());
        Thread.sleep(5000);
        onView(withText(containsString("Hoeksken"))).check(matches(isDisplayed()));
    }

    //Search with one or more characters. More characters can be added
    @Test
    public void searchOneOrMoreCharacters() throws InterruptedException {
        // Type text and then press the button.
        onView(withId(R.id.search))
                .perform(typeText(CHARACTER_TO_BE_TYPED), closeSoftKeyboard());
        onView(withId(R.id.search)).perform(click());
        Thread.sleep(7000);
        ListView listView = (ListView) activityTestRule.getActivity().findViewById(R.id.citiesList);
        View view = listView.getChildAt(0);
        TextView textView = view.findViewById(R.id.cityName);
        String search = textView.getText().toString().toLowerCase();
        assertTrue(search.toLowerCase().contains(CHARACTER_TO_BE_TYPED.toLowerCase()));
    }


    //Search for an existing city
    @Test
    public void searchWithExistingCity() throws Exception {
        onView(withId(R.id.search))
                .perform(typeText(STRING_TO_BE_TYPED), closeSoftKeyboard());
        onView(withId(R.id.search)).perform(click());
        Thread.sleep(7000);
        ListView listView = (ListView) activityTestRule.getActivity().findViewById(R.id.citiesList);
        int k = listView.getChildCount();

        for (int i = 0; i < k; i++) {
            View view = listView.getChildAt(i);
            TextView textView = view.findViewById(R.id.cityName);
            String search = textView.getText().toString().toLowerCase();
            assertTrue(search.toLowerCase().contains(STRING_TO_BE_TYPED.toLowerCase()));
        }
    }

    //Search for a non-existing city
    @Test
    public void searchWithNonExistingCity() throws Exception {
        onView(withId(R.id.search))
                .perform(typeText(NON_EXISTING_CITY_TO_BE_TYPED), closeSoftKeyboard());
        onView(withId(R.id.search)).perform(click());
        Thread.sleep(7000);
        onView(withId(R.id.citiesList)).check(matches(not(isDisplayed())));
    }


    //Scroll on List of Cities view
    @Test
    public void scrollOnListView() throws InterruptedException {
        Thread.sleep(7000);
        onView(withId(R.id.citiesList)).perform(swipeUp());
        Thread.sleep(7000);
        ListView listView = (ListView) activityTestRule.getActivity().findViewById(R.id.citiesList);
        assertTrue(hasMinimumChildCount(listView.getChildCount() - 1).matches(listView));
    }

    //Select a city from List of cities
    @Test
    public void selectCity() throws InterruptedException {
        onData(allOf()).inAdapterView(withId(R.id.citiesList)).atPosition(0).perform(click());
        Thread.sleep(7000);
        onView(withId(R.id.insert_point)).check(matches(isDisplayed()));
        onView(withContentDescription("Google Map")).check(matches(isDisplayed()));

    }
}
