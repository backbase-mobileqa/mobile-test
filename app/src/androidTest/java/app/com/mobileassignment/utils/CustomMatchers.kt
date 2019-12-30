package app.com.mobileassignment.utils

import androidx.test.espresso.matcher.BoundedMatcher
import app.com.mobileassignment.model.City
import org.hamcrest.Description
import org.hamcrest.Matcher

fun withCityName(cityName: String): Matcher<Any> {
    return object : BoundedMatcher<Any, City>(City::class.java) {
        override fun matchesSafely(city: City): Boolean {
            return cityName == city.name
        }

        override fun describeTo(description: Description) {
            description.appendText("with id: $cityName")
        }
    }
}