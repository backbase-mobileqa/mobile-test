package app.com.mobileassignment.uitests

import android.app.Activity
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import app.com.mobileassignment.R
import app.com.mobileassignment.utils.isKeyboardOpen
import app.com.mobileassignment.views.MainActivity
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class KeyboardTests {

    @get:Rule
    private var activityRule = ActivityTestRule(MainActivity::class.java, true, true)

    @Before
    fun launchActivity() {
        ActivityScenario.launch(MainActivity::class.java)
    }


    fun getCurrentActivity(): Activity? {
        var currentActivity: Activity? = null
        getInstrumentation().runOnMainSync { run { currentActivity =
                ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED).elementAtOrNull(0) } }
        return currentActivity
    }

    @Test
    fun keyboardActivatesWhenTappingOnSearch() {
        Espresso.onView(ViewMatchers.withId(R.id.search)).perform(ViewActions.click())
        getCurrentActivity()?.isKeyboardOpen()?.let {
            assertTrue(it)
        }
    }

    @Test
    fun keyboardDeactivatesOnBack() {
        Espresso.onView(ViewMatchers.withId(R.id.search)).perform(ViewActions.click())
        pressBack()
        Thread.sleep(5000)
        getCurrentActivity()?.isKeyboardOpen()?.let {
            assertFalse(it)
        }
    }

    @Test
    fun keyboardDoesNotOpenAtStart() {
        Thread.sleep(1000)
        getCurrentActivity()?.isKeyboardOpen()?.let {
            assertFalse(it)
        }
    }

}