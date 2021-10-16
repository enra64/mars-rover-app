package de.arneherdick.thermondorover

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import de.arneherdick.thermondorover.photo_list.ui.MarsPhotoListItemViewHolder
import org.junit.Test
import java.lang.Thread.sleep

class BasicUsageTest {
    @Test
    fun basicUsageTest() {
        ActivityScenario.launch(MarsRoverPhotoActivity::class.java)
        sleep(4000)
        selectPhoto()
        sharePhoto()
    }

    private fun selectPhoto() {
        onView(withId(R.id.photo_list)).perform(RecyclerViewActions.actionOnItemAtPosition< MarsPhotoListItemViewHolder>(0, click()))
        onView(withId(R.id.photo)).check(matches(isDisplayed()))
    }

    private fun sharePhoto() {
        onView(withId(R.id.share)).perform(click())
    }
}
