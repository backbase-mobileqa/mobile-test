package app.com.mobileassignment;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import app.com.mobileassignment.views.MainActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.assertion.ViewAssertions.selectedDescendantsMatch;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.startsWith;


@RunWith(AndroidJUnit4.class)
public class MainActivityTests {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule
            = new ActivityTestRule<>(MainActivity.class);

    // This Test is to validate the searching functionality.
    @Test
    public void searchFunctionality() {
        onView(withId(R.id.search))
                .perform(typeText("Eagan"), closeSoftKeyboard());
        // ToDO: get the list size, then loop on the list to validate the start of each city
        onView(withText("Eagan")).check(matches(isDisplayed()));
        onView(withId(R.id.search)).perform(clearText(),typeText("blabla"),closeSoftKeyboard());
        onView(withText("No city matching the serach criteria")).check(matches(isDisplayed()));
    }

    //This test to Validate the search functionality with Special characters.
    @Test
    public void searchWithSpecialChars() {
        onView(withId(R.id.search))
                .perform(typeText("'"), closeSoftKeyboard());
       // onView(withId(R.id.citiesList)).
        // ToDO: get the list size, then loop on the list to validate the start of each city
        onData(anything()).inAdapterView(withId(R.id.citiesList)).atPosition(0).onChildView(withId(R.id.cityName)).check(matches(withText(startsWith("'"))));
    }
}
