package com.example.kitchenpal;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.ext.truth.content.IntentSubject.assertThat;

import androidx.test.espresso.core.internal.deps.guava.collect.Iterables;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginTest {

    @Before
    public void intentsInit() {
        Intents.init();
    }

    @After
    public void intentsTeardown() {
        Intents.release();
    }

    @After
    public void tearDown() throws Exception {
        activityScenarioRule.getScenario().close();
    }

    @Rule
    public ActivityScenarioRule<Login> activityScenarioRule = new ActivityScenarioRule<Login>(Login.class);


    @Test
    public void checkVisibilityLoginEmail(){
        onView(withId(R.id.loginEmail)).perform(typeText("test1"));
        onView(withId(R.id.loginEmail)).check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }


    @Test
    public void checkVisibilityLoginPassword(){
        onView(withId(R.id.loginPassword)).perform(typeText("password"));
        onView(withId(R.id.loginPassword)).check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }


    @Test
    public void checkSignupLinkClickable() {
        onView(withId(R.id.signUpLink)).check(matches(isClickable()));
    }


    @Test
    public void checkLoginButtonClickable() {
        onView(withId(R.id.loginButton)).check(matches(isClickable()));
    }


    @Test
    public void testSignupIntent() {
        onView(withId(R.id.signUpLink)).perform(click());
        assertThat(Iterables.getOnlyElement(Intents.getIntents())).hasComponentClass(SignUp.class);
    }

}