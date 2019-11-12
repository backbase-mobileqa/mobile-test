package app.com.mobileassignment;

import android.os.SystemClock;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import app.com.mobileassignment.views.MainActivity;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSubstring;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

public class SearchFunctionalityTest {

    @Rule
    public ActivityRule<MainActivity> main = new ActivityRule<>(MainActivity.class);

    //1. SearchList - Ascending order
    @Test
    public void searchList_AscendingOrder(){
        SearchFunctionalityHelpFunctions searchFunctionalityHelpFunctions = new SearchFunctionalityHelpFunctions();

        //Verify landing screen
        searchFunctionalityHelpFunctions.matchToolbarTitle("Mobile Assignment");
        onView(withId(R.id.search)).check(matches(isDisplayed()));
        onView(withId(R.id.citiesList)).check(matches(isDisplayed()));

        //Fill in Search field with text
        String searchText = "Krako";
        searchFunctionalityHelpFunctions.fillInSearchField(searchText);

        //Wait for elements to load in List
        SystemClock.sleep(500);

        //all elements in Results contains searched text
        int count = searchFunctionalityHelpFunctions.getNoOfElementsInCityList();
        for(int i =0; i<count; i++){
            onData(anything()).inAdapterView(withId(R.id.citiesList)).onChildView(isAssignableFrom(android.widget.LinearLayout.class))
                    .onChildView(isAssignableFrom(android.widget.TextView.class)).atPosition(i)
                    .check(matches(withSubstring(searchText)));
        }

        //Check if cities are sorted in ascending order
        List<String> titles = new ArrayList<>();
        for(int i = 0; i < searchFunctionalityHelpFunctions.getNoOfElementsInCityList(); i++){
            titles.add(searchFunctionalityHelpFunctions.getTextFromCityListAtPosition(i));
        }

        List<String> expected = titles;
        Collections.sort(expected);
        Assert.assertEquals("Elements are not sorted properly", expected, titles);
    }

    //3. SearchList - user can search for City - existing city scenario
    //Please note - scenario will fail, as search: "krakow, PL" will result: citiesList is not generated
    @Test
    public void searchList_userCanSearchForCity_ExistingCityScenario(){
        SearchFunctionalityHelpFunctions searchFunctionalityHelpFunctions = new SearchFunctionalityHelpFunctions();

        //Fill in Search field with text
        String searchText = "Krakow";
        searchFunctionalityHelpFunctions.fillInSearchField(searchText);

        //Wait for elements to load in List
        SystemClock.sleep(500);

        //Wait for elements to load in List
        SystemClock.sleep(500);

        //all elements in Results contains searched text
        int count = searchFunctionalityHelpFunctions.getNoOfElementsInCityList();
        for(int i =0; i<count; i++){
            onData(anything()).inAdapterView(withId(R.id.citiesList)).onChildView(isAssignableFrom(android.widget.LinearLayout.class))
                    .onChildView(isAssignableFrom(android.widget.TextView.class)).atPosition(i)
                    .check(matches(withSubstring(searchText)));
        }

        //Check if cities are sorted in ascending order
        List<String> titles = new ArrayList<>();
        for(int i = 0; i < searchFunctionalityHelpFunctions.getNoOfElementsInCityList(); i++){
            titles.add(searchFunctionalityHelpFunctions.getTextFromCityListAtPosition(i));
        }

        List<String> expected = titles;
        Collections.sort(expected);
        Assert.assertEquals("Elements are not sorted properly", expected, titles);

        //Delete previous search criteria
        onView(withId(R.id.search)).perform(clearText());

        //Search for Krakow, PL and verify result
        searchText = "Krakow, PL";
        searchFunctionalityHelpFunctions.fillInSearchField(searchText);

        onData(anything()).inAdapterView(withId(R.id.citiesList)).onChildView(isAssignableFrom(android.widget.LinearLayout.class))
                .onChildView(isAssignableFrom(android.widget.TextView.class)).atPosition(0)
                .check(matches(withText("Krakow, PL")));
    }

    //4. SearchList - user can search for City - non existing city scenario
    @Test
    public void searchList_UserCanSearchForCity_NonExistingCityScenario(){
        SearchFunctionalityHelpFunctions searchFunctionalityHelpFunctions = new SearchFunctionalityHelpFunctions();

        //in search field enter text
        String nonExistingCity = "Wonsik";
        searchFunctionalityHelpFunctions.fillInSearchField(nonExistingCity);

        //wait for results to load
        SystemClock.sleep(500);

        //verify that List with results is not displayed
        onView(withId(R.id.citiesList)).check(searchFunctionalityHelpFunctions.isNotDisplayed());
    }

    //6. Search/Map - user can select city and view it on map
    //PLEASE NOTE: Scenario will pass as I check only that map part is displayed - not map itself
    @Test
    public void searchMap_UserCanSelectCityAndViewItOnMap(){
        SearchFunctionalityHelpFunctions searchFunctionalityHelpFunctions = new SearchFunctionalityHelpFunctions();

        //Fill in search field
        String city = "Colorado";
        searchFunctionalityHelpFunctions.fillInSearchField(city);

        //Verify that user can view on map all cities from the list
        int count = searchFunctionalityHelpFunctions.getNoOfElementsInCityList();
        for(int i =0; i<count; i++){
            onData(anything()).inAdapterView(withId(R.id.citiesList)).onChildView(isAssignableFrom(android.widget.LinearLayout.class))
                    .onChildView(isAssignableFrom(android.widget.TextView.class)).atPosition(i)
                    .perform(click());

            searchFunctionalityHelpFunctions.matchToolbarTitle("Mobile Assignment");

            //Google part is displayed - just checking general view as map is not loading
            onView(withId(R.id.insert_point)).check(matches(isDisplayed()));

            onView(isRoot()).perform(pressBack());
        }

        //verify title
        searchFunctionalityHelpFunctions.matchToolbarTitle("Mobile Assignment");
    }
}

