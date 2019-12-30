package app.com.mobileassignment.uitests

import android.widget.EditText
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.com.mobileassignment.R
import app.com.mobileassignment.model.City
import app.com.mobileassignment.utils.withCityName
import app.com.mobileassignment.views.MainActivity
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

const val STRING_TO_BE_SCROLLED = "Bucharest, RO"
const val STRING_TO_BE_SCROLLED_INDEX = "Aasiaat, GL"



@RunWith(AndroidJUnit4::class)
class ScrollTests {

    @Before
    fun launchActivity() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    @Throws(InterruptedException::class)
    fun scrollingToACity() {
        Espresso.onData(Matchers.allOf(Matchers.`is`(Matchers.instanceOf(City::class.java)),
                withCityName(STRING_TO_BE_SEARCHED)))
                .inAdapterView(ViewMatchers.withId(R.id.citiesList))
                .perform(ViewActions.scrollTo())
        Thread.sleep(5000)
        Espresso.onView(ViewMatchers.withText(STRING_TO_BE_SCROLLED)).
                check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    @Throws(InterruptedException::class)
    fun scrollToIndexCity() {
        Espresso.onData(Matchers.anything()).inAdapterView(ViewMatchers.withId(R.id.citiesList)).
                atPosition(50).perform(ViewActions.swipeUp())
        Thread.sleep(5000)
        Espresso.onView(ViewMatchers.withText(STRING_TO_BE_SCROLLED_INDEX)).
                check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    @Throws(InterruptedException::class)
    fun scrollCloselyToTheBottomOfTheList() {
        Espresso.onData(Matchers.anything()).inAdapterView(ViewMatchers.withId(R.id.citiesList)).
                atPosition(209000).perform(ViewActions.swipeUp())
        Thread.sleep(5000)
    }

    @Test
    @Throws(InterruptedException::class)
    fun scrollAfterKeyboardOpened() {
        Espresso.onView(withId(R.id.search)).perform(ViewActions.click())
        Espresso.onData(Matchers.allOf(Matchers.`is`(Matchers.instanceOf(City::class.java)),
                withCityName(STRING_TO_BE_SEARCHED)))
                .inAdapterView(withId(R.id.citiesList))
                .perform(ViewActions.scrollTo())
        Thread.sleep(5000)
        Espresso.onView(ViewMatchers.withText(STRING_TO_BE_SCROLLED)).
                check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

    @Test
    @Throws(InterruptedException::class)
    fun scrollAfterSearchingInCityLists() {

        Espresso.onView(withId(R.id.search)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.isAssignableFrom(EditText::class.java)).
                perform(ViewActions.typeText(STRING_TO_BE_SCROLLED.first().toString()),
                        ViewActions.pressImeActionButton())
        Thread.sleep(5000)
        Espresso.onData(Matchers.allOf(Matchers.`is`(Matchers.instanceOf(City::class.java)),
                withCityName(STRING_TO_BE_SEARCHED)))
                .inAdapterView(withId(R.id.citiesList))
                .perform(ViewActions.scrollTo())
        Thread.sleep(1000)
        Espresso.onView(ViewMatchers.withText(STRING_TO_BE_SCROLLED)).
                check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }
}