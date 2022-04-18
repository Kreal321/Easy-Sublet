package com.example.easysublet.view;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

import android.view.View;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.easysublet.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<MainActivity>(MainActivity.class);
    public View decorView;

    @Before
    public void setUp(){
        activityScenarioRule.getScenario().onActivity(new ActivityScenario.ActivityAction<MainActivity>() {
            @Override
            public void perform(MainActivity activity) {
                decorView = activity.getWindow().getDecorView();
            }
        });
    }

    @Test
    public void testLoginFailed(){
        onView(withId(R.id.emailEntry)).perform(typeText("test@1.com"),closeSoftKeyboard());
        onView(withId(R.id.passwordEntry)).perform(typeText("thisiswrong"),closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());
        onView(withText(R.string.login_error)).inRoot(withDecorView(not(decorView))).check(matches(isDisplayed()));
    }

    @Test
    public void testLogin(){
        onView(withId(R.id.emailEntry)).perform(typeText("test@1.com"),closeSoftKeyboard());
        onView(withId(R.id.passwordEntry)).perform(typeText("123456"),closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());
        onView(withText(R.string.login_success)).inRoot(withDecorView(not(decorView))).check(matches(isDisplayed()));
    }

    @Test
    public void testSignUp(){
        onView(withId(R.id.signUpBtn)).perform(click());
        onView(withId(R.id.usernameEntry)).perform(typeText("test"),closeSoftKeyboard());
        onView(withId(R.id.emailEntry)).perform(typeText("test@11.com"),closeSoftKeyboard());
        onView(withId(R.id.passwordEntry)).perform(typeText("123456"),closeSoftKeyboard());
        onView(withId(R.id.confirmPasswordEntry)).perform(typeText("123456"),closeSoftKeyboard());
        onView(withId(R.id.signUpBtn)).perform(click());
        onView(withText(R.string.register_succeeded)).inRoot(withDecorView(not(decorView))).check(matches(isDisplayed()));
    }

}