package de.arneherdick.thermondorover.photo_list.ui.view_models

import de.arneherdick.thermondorover.getMarsPhoto
import io.mockk.*
import org.junit.Assert.*
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
