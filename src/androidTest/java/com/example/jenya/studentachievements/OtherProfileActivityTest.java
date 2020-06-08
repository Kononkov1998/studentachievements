package com.example.jenya.studentachievements;

import android.content.Intent;

import androidx.test.rule.ActivityTestRule;

import com.example.jenya.studentachievements.activities.FavoritesActivity;
import com.example.jenya.studentachievements.activities.OtherProfileActivity;
import com.example.jenya.studentachievements.models.FullName;
import com.example.jenya.studentachievements.models.Group;
import com.example.jenya.studentachievements.models.UserInfo;

import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class OtherProfileActivityTest {

    @Rule
    public ActivityTestRule<OtherProfileActivity> rule = new ActivityTestRule<>(OtherProfileActivity.class, true, false);

    @Test
    public void fullNameAndGroupCheck() {
        Intent intent = new Intent();
        UserInfo student = new UserInfo();
        student.setGroup(new Group());
        student.getGroup().setName("Test group name");
        student.setAchievements(new ArrayList<>());
        student.setFullName(new FullName());
        student.getFullName().setFirstName("abc");
        student.getFullName().setLastName("test last name");
        student.getFullName().setPatronymic("");

        UserInfo currentUser = new UserInfo();
        currentUser.setFavouriteStudents(new ArrayList<>());
        UserInfo.setCurrentUser(currentUser);

        intent.putExtra("otherStudent", student);
        intent.putExtra("activity", FavoritesActivity.class.getSimpleName());
        rule.launchActivity(intent);

        //группа совпадает
        onView(withId(R.id.groupProfile)).check(matches(withText(student.getGroup().getName())));
        //ФИО совпадает
        onView(withId(R.id.textProfile)).check(matches(withText(
                String.format("%s\n%s\n%s", student.getFullName().getLastName(), student.getFullName().getFirstName(), student.getFullName().getPatronymic()))));
    }
}