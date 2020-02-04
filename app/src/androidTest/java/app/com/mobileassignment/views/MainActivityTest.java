package app.com.mobileassignment.views;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;

import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;

import app.com.mobileassignment.R;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private static final String validCityName = "Zimapan";
    private static final String validSingleLetter = "P";
    private static final String inValidSearch = ",,";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void launchAppTest() {
        onView(withId(R.id.search)).check(matches(withHint("Search")));
        onView(withId(R.id.citiesList)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void searchWithValidCityName() throws Exception {
        onView(withId(R.id.search)).perform(typeText(validCityName), closeSoftKeyboard());
        Thread.sleep(1000);
        onView(withId(R.id.cityName)).check(matches(isCompletelyDisplayed())).perform(click());
        onView(withContentDescription("Google Map")).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void swipeUpCityList(){
        // If you want to scroll to the end of the list, provide 209556 in atPosition in the below line
        onData(anything()).inAdapterView(allOf(withId(R.id.citiesList), isDisplayed())).atPosition(100).perform(swipeUp());
    }

    @Test
    public void validSingleLetterSearch() throws Exception {
        onView(withId(R.id.search)).perform(typeText(validSingleLetter), closeSoftKeyboard());
        Thread.sleep(500);
        onData(anything()).inAdapterView(withId(R.id.citiesList)).atPosition(1).perform(click());
        onView(withContentDescription("Google Map")).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void invalidSearchResultsInNoList() throws Exception {
        onView(withId(R.id.search)).perform(typeText(inValidSearch), closeSoftKeyboard());
        Thread.sleep(500);
        onView(withId(R.id.cityName)).check(ViewAssertions.doesNotExist());
    }
}
