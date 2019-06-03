package com.jbseppanen.nikecodechallenge

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class RecyclerViewTests {
    @get:Rule
    val activityTestRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java, false, true)

    @Before
    fun registerIdlingResource() {

    }



    @Test
    fun clickOnEachItem() {
        Thread.sleep(5000)  // Delay to allow data to load.  Should also be possible using an idling resource.
        val rView = activityTestRule.activity.findViewById(R.id.recycler_view) as RecyclerView
        val itemCount = rView.adapter?.itemCount
        if (itemCount != null) {
            for (i in 0 until itemCount) {
                onView(withId(R.id.recycler_view)).perform(
                    RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                        i,
                        ViewActions.scrollTo()
                    )
                )
                onView(withId(R.id.recycler_view)).perform(
                    RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                        i,
                        click()
                    )
                )
                Espresso.pressBack()
            }
        }
    }
}
