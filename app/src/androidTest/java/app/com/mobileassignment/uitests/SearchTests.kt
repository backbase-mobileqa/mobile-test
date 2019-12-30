package app.com.mobileassignment.uitests

import android.widget.EditText
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.com.mobileassignment.R
import app.com.mobileassignment.model.City
import app.com.mobileassignment.utils.withCityName
import app.com.mobileassignment.views.MainActivity
import junit.framework.Assert.assertTrue
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


const val STRING_TO_BE_SEARCHED = "Bucharest"
const val STRING_TO_BE_SEARCHED_WITH_COUNTRY = "Bucharest, RO"

@RunWith(AndroidJUnit4::class)
class SearchTests {

    @Before
    fun launchActivity() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    @Throws(InterruptedException::class)
    fun searchingForAValidCityWithoutCountry() {
        Espresso.onView(ViewMatchers.withId(R.id.search)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.isAssignableFrom(EditText::class.java)).
                perform(ViewActions.typeText(STRING_TO_BE_SEARCHED),
                        ViewActions.pressImeActionButton())
        Thread.sleep(5000)
        Espresso.onView(ViewMatchers.withText(STRING_TO_BE_SEARCHED)).
                check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

    @Test
    @Throws(InterruptedException::class)
    fun searchingForAValidCityWithCountry() {
        Espresso.onView(ViewMatchers.withId(R.id.search)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.isAssignableFrom(EditText::class.java)).
                perform(ViewActions.typeText(STRING_TO_BE_SEARCHED_WITH_COUNTRY),
                        ViewActions.pressImeActionButton())
        Thread.sleep(5000)
        Espresso.onData(Matchers.allOf(Matchers.`is`(Matchers.instanceOf(City::class.java)),
                withCityName(STRING_TO_BE_SEARCHED_WITH_COUNTRY)))
                .inAdapterView(ViewMatchers.withId(R.id.citiesList)).
                        check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    @Throws(InterruptedException::class)
    fun searchingForEmptyString() {
        val array = ArrayList<Int>(10)
        val result = array.isEmpty()

        Espresso.onView(ViewMatchers.withId(R.id.search)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.isAssignableFrom(EditText::class.java)).
                perform(ViewActions.typeText(" "),
                        ViewActions.pressImeActionButton())
        Thread.sleep(5000)
        assertTrue(result)
    }

    @Test
    @Throws(InterruptedException::class)
    fun searchingForInvalidString() {
        val array = ArrayList<Int>(10)
        val result = array.isEmpty()

        Espresso.onView(ViewMatchers.withId(R.id.search)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.isAssignableFrom(EditText::class.java)).
                perform(ViewActions.typeText("abcdef"),
                        ViewActions.pressImeActionButton())
        Thread.sleep(5000)

        assert(result)
    }

    @Test
    fun searchingForPartialString() {
        Espresso.onView(ViewMatchers.withId(R.id.search)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.isAssignableFrom(EditText::class.java)).
                perform(ViewActions.typeText("'t "),
                        ViewActions.pressImeActionButton())
        Thread.sleep(5000)
        Espresso.onView(ViewMatchers.withId(R.id.citiesList)).
                check(ViewAssertions.matches(ViewMatchers.isDisplayed())).
                check(ViewAssertions.matches(ViewMatchers.hasChildCount(2)))
    }

    @Test
    @Throws(InterruptedException::class)
    fun searchingForLongString() {
        val array = ArrayList<Int>(10)
        val result = array.isEmpty()

        Espresso.onView(ViewMatchers.withId(R.id.search)).perform(ViewActions.click())
        val testString = "abcdefghijklmnopqrstuvwxyz"
        val builder = StringBuilder()
        for (x in 0..1000)
            builder.append(testString)

        Espresso.onView(ViewMatchers.isAssignableFrom(EditText::class.java)).
                perform(ViewActions.typeText(builder.toString()),
                        ViewActions.pressImeActionButton())
        Thread.sleep(5000)

        assert(result)
    }
}