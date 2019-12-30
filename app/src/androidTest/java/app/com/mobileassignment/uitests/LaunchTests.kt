package app.com.mobileassignment.uitests

import android.util.Log
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.com.mobileassignment.R
import app.com.mobileassignment.utils.getTextFromCityListAtPosition
import app.com.mobileassignment.views.MainActivity
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

const val MAIN_APPLICATION_TITLE = "Mobile Assignment"

@RunWith(AndroidJUnit4::class)
class LaunchTests {

    @Before
    fun launchActivity() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun appNameIsDisplayedAfterLaunchingApp() {
        Espresso.onView(ViewMatchers.withText(MAIN_APPLICATION_TITLE)).
                check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun searchIsDisplayedAfterLaunchingApp() {
        Espresso.onView(ViewMatchers.withId(R.id.search)).
                check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun citiesListAppIsDisplayedAfterLaunchingApp() {
        Espresso.onView(ViewMatchers.withId(R.id.citiesList)).
                check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    /**
     * Obs. testing that the list is sorted should be moved inside an unit test!
     */
    @Test
    fun citiesListIsSortedSamplingItems() {
        val beforeRandomElement =  (0..209555).random()
        val afterRandomElement = ((beforeRandomElement+1)..209556).random()
        val beforeText = getTextFromCityListAtPosition(beforeRandomElement)
        val afterText = getTextFromCityListAtPosition(afterRandomElement)
        Log.e("Test sort cities name", "Comparing cities \""+ beforeText + "\" with \"" + afterText +"\"")
        assertTrue(beforeText.toString().compareTo(afterText.toString()) < 0 )
    }
}