package com.example.jenya.studentachievements;

import android.content.Intent;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import com.example.jenya.studentachievements.activities.ProfileActivity;
import com.example.jenya.studentachievements.models.FullName;
import com.example.jenya.studentachievements.models.Group;
import com.example.jenya.studentachievements.models.UserInfo;

import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

public class NavigationTest {

    @Rule
    public ActivityTestRule<ProfileActivity> rule = new ActivityTestRule<>(ProfileActivity.class, true, false);

    @Test
    public void check() {
        UserInfo currentUser = new UserInfo();
        currentUser.setFavouriteStudents(new ArrayList<>());
        currentUser.setAchievements(new ArrayList<>());
        FullName fullName = new FullName();
        fullName.setLastName("test");
        fullName.setFirstName("hello");
        fullName.setPatronymic("world");
        currentUser.setFullName(fullName);
        currentUser.setGroup(new Group());
        currentUser.getGroup().setName("test group name :)");
        currentUser.setVisibility("me");
        UserInfo.setCurrentUser(currentUser);
        rule.launchActivity(new Intent());

        //группа совпадает
        onView(withId(R.id.groupProfile)).check(matches(withText(currentUser.getGroup().getName())));
        //ФИО совпадает
        onView(withId(R.id.textProfile)).check(matches(withText(
                String.format("%s\n%s\n%s", currentUser.getFullName().getLastName(), currentUser.getFullName().getFirstName(), currentUser.getFullName().getPatronymic()))));
        //переходим в избранных
        onView(withId(R.id.imageFavorites)).perform(click());
        onView(withId(R.id.empty)).check(matches(allOf(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE), withText(R.string.no_favorites))));
        //переходим в рейтинг
        onView(withId(R.id.imageTop)).perform(click());
        onView(withId(R.id.place)).check(matches(isDisplayed()));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //обратно в профиль и в настройки
        onView(withId(R.id.imageProfile)).perform(click());
        onView(withId(R.id.checkboxHide)).check(matches(isDisplayed()));
        onView(withId(R.id.imageSettings)).perform(click());
        onView(withId(R.id.buttonExit)).perform(click());
    }
}