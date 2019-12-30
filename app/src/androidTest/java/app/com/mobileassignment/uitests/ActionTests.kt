package app.com.mobileassignment.uitests

import android.widget.EditText
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.com.mobileassignment.R
import app.com.mobileassignment.views.MainActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

const val STRING_TO_BE_TAPPED = "665 Site Colonia, US"

@RunWith(AndroidJUnit4::class)
class ActionTests {

    @Before
    fun launchActivity() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    @Throws(InterruptedException::class)
    fun tappingOnACity() {
        Espresso.onView(ViewMatchers.withText(STRING_TO_BE_TAPPED)).
                perform(ViewActions.click())
        Thread.sleep(5000)
        Espresso.onView(ViewMatchers.withId(R.id.insert_point)).
                check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    @Throws(InterruptedException::class)
    fun doubleTappingOnACity() {
        Thread.sleep(5000)
        Espresso.onView(ViewMatchers.withText(STRING_TO_BE_TAPPED)).
                perform(ViewActions.doubleClick())
        Thread.sleep(5000)
        Espresso.pressBack()
        Espresso.onView(ViewMatchers.withText(STRING_TO_BE_TAPPED)).
                check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    @Throws(InterruptedException::class)
    fun tappingOnASearchedCity() {
        Espresso.onView(ViewMatchers.withId(R.id.search)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.isAssignableFrom(EditText::class.java)).
                perform(ViewActions.typeText(STRING_TO_BE_SEARCHED),
                        ViewActions.pressImeActionButton())
        Thread.sleep(5000)
        Espresso.onView(ViewMatchers.withText(STRING_TO_BE_SEARCHED_WITH_COUNTRY)).
                perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.insert_point)).
                check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}