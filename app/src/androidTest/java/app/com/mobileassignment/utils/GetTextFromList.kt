package app.com.mobileassignment.utils

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.test.espresso.Espresso
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import app.com.mobileassignment.R
import org.hamcrest.Matcher
import org.hamcrest.Matchers

fun getTextFromCityListAtPosition(position: Int): String? {
    val stringHolder = arrayOf<String?>(null)
    Espresso.onData(Matchers.anything()).inAdapterView(ViewMatchers.withId(R.id.citiesList)).onChildView(ViewMatchers.isAssignableFrom(LinearLayout::class.java))
            .onChildView(ViewMatchers.isAssignableFrom(TextView::class.java)).atPosition(position).perform(object : ViewAction {
                override fun getConstraints(): Matcher<View> {
                    return ViewMatchers.isAssignableFrom(TextView::class.java)
                }

                override fun getDescription(): String {
                    return "getting text from a TextView"
                }

                override fun perform(uiController: UiController?, view: View) {
                    val tv = view as TextView
                    stringHolder[0] = tv.text.toString()
                }
            })
    return stringHolder[0]
}