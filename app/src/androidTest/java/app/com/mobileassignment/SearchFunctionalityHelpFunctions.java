package app.com.mobileassignment;

import android.view.View;
import android.widget.TextView;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.util.HumanReadables;

import org.hamcrest.Matcher;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withSubstring;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.core.AllOf.allOf;

public class SearchFunctionalityHelpFunctions {

    public ViewInteraction matchToolbarTitle(String title){
        return onView(allOf(isAssignableFrom(android.widget.TextView.class), withParent(withId(R.id.action_bar)))).check(matches(withText(title)));
    }

    public int getNoOfElementsInCityList(){
        int count = 0;

        for(int i =0; i < 18; i++)
            try{
                onData(anything()).inAdapterView(withId(R.id.citiesList)).atPosition(i).check(matches(isDisplayed()));
                count++;
            }
            catch (Exception ignored){}

        return count;
    }

    public String getTextFromCityListAtPosition(int position) {
        final String[] stringHolder = { null };
        onData(anything()).inAdapterView(withId(R.id.citiesList)).onChildView(isAssignableFrom(android.widget.LinearLayout.class))
                .onChildView(isAssignableFrom(android.widget.TextView.class)).atPosition(position).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(TextView.class);
            }

            @Override
            public String getDescription() {
                return "getting text from a TextView";
            }

            @Override
            public void perform(UiController uiController, View view) {
                TextView tv = (TextView)view; //Save, because of check in getConstraints()
                stringHolder[0] = tv.getText().toString();
            }
        });
        return stringHolder[0];
    }

    public static ViewAssertion isNotDisplayed() {
        return new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException noView) {
                if (view != null && isDisplayed().matches(view)) {
                    throw new AssertionError("View is present in the hierarchy and Displayed: "
                            + HumanReadables.describe(view));
                }
            }
        };
    }

    public void fillInSearchField(String text){
        String[] splittedText = text.split("");
        for(int i = 0; i < splittedText.length; i++){
            onView(withId(R.id.search)).perform(ViewActions.typeText(splittedText[i]));
        }
    }
}
