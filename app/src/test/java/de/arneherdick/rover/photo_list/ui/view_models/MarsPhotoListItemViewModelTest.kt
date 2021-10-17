package de.arneherdick.rover.photo_list.ui.view_models

import de.arneherdick.rover.getMarsPhoto
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Test

class MarsPhotoListItemViewModelTest {
    private val photo = getMarsPhoto()
    private val model = MarsPhotoListItemViewModel(photo) {}

    @Test
    fun getTitle() {
        assertEquals(photo.rover.name, model.title)
    }

    @Test
    fun getSubTitle() {
        assertEquals(photo.camera.name, model.subTitle)
    }

    @Test
    fun getSubSubTitle() {
        assertEquals("Jan 20, 2015", model.subSubTitle)
    }

    @Test
    fun onClick() {
        val mockedCallback = mockk<() -> Unit>()
        every { mockedCallback.invoke() } just runs
        val mockedModel = MarsPhotoListItemViewModel(photo, mockedCallback)
        mockedModel.onClick()
        verify(exactly = 1) { mockedCallback() }
    }
}
