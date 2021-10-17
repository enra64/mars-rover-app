package de.arneherdick.thermondorover.photo_details.data

import de.arneherdick.thermondorover.Utils
import de.arneherdick.thermondorover.Utils.formatDate
import de.arneherdick.thermondorover.getMarsPhoto
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MarsPhotoDetailViewModelTest {

    @Before
    fun fixDateFormatting() {
        mockkObject(Utils)
        every { formatDate(any()) } returns "Jan 20, 2015"
    }

    @After
    fun unmockAll() {
        unmockkAll()
    }

    @Test
    fun getPhotoId() {
        val photo = getMarsPhoto()
        val model = MarsPhotoDetailViewModel(photo)
        assertEquals(photo.id, model.photoId)
    }

    @Test
    fun getSol() {
        val photo = getMarsPhoto()
        val model = MarsPhotoDetailViewModel(photo)
        assertEquals(photo.sol, model.sol)
    }

    @Test
    fun getEarthDate() {
        val photo = getMarsPhoto()
        val model = MarsPhotoDetailViewModel(photo)
        assertEquals("Jan 20, 2015", model.earthDate)
    }

    @Test
    fun getCamera() {
        val photo = getMarsPhoto()
        val model = MarsPhotoDetailViewModel(photo)
        assertEquals(photo.camera, model.camera)
    }

    @Test
    fun getRover() {
        val photo = getMarsPhoto()
        val model = MarsPhotoDetailViewModel(photo)
        assertEquals(photo.camera, model.camera)
    }

    @Test
    fun getLaunchDate() {
        val photo = getMarsPhoto()
        val model = MarsPhotoDetailViewModel(photo)
        assertEquals("Jan 20, 2015", model.launchDate)
    }

    @Test
    fun getLandingDate() {
        val photo = getMarsPhoto()
        val model = MarsPhotoDetailViewModel(photo)
        assertEquals("Jan 20, 2015", model.landingDate)
    }

    @Test
    fun getUrl() {
        val photo = getMarsPhoto()
        val model = MarsPhotoDetailViewModel(photo)
        assertEquals(photo.imageUrl, model.url)
    }
}
