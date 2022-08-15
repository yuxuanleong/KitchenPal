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
public class SignUpTest {

    @Rule
    public ActivityScenarioRule<SignUp> activityScenarioRule = new ActivityScenarioRule<SignUp>(SignUp.class);

    @After
    public void tearDown() throws Exception {
        activityScenarioRule.getScenario().close();
    }


    @Test
    public void checkVisibilitySignupEmail(){
        onView(withId(R.id.signupEmail)).perform(typeText("test@gmail.com"));
        onView(withId(R.id.signupEmail)).check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void checkVisibilitySignupUsername(){
        onView(withId(R.id.username)).perform(typeText("testUsername"));
        onView(withId(R.id.username)).check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void checkVisibilitySignupPassword(){
        onView(withId(R.id.signupPassword)).perform(typeText("passwordtest"));
        onView(withId(R.id.signupPassword)).check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void checkVisibilitySignupReenterPassword(){
        onView(withId(R.id.signupEmail)).perform(typeText("passwordtest"));
        onView(withId(R.id.signupEmail)).check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void checkLoginButtonClickable() {
        onView(withId(R.id.signupButton)).check(matches(isClickable()));
    }

    @Before
    public void intentsInit() {
        Intents.init();
    }

    @After
    public void intentsTeardown() {
        Intents.release();
    }

    @Test
    public void testLoginIntent() {
        onView(withId(R.id.loginLink)).perform(click());
        assertThat(Iterables.getOnlyElement(Intents.getIntents())).hasComponentClass(Login.class);
    }
}