package de.arneherdick.thermondorover.mars_rover_api

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Test
import java.text.SimpleDateFormat

class MarsRoverApiTest {
    /**
     * Used to quickly get a Date instance from a yyyy-MM-dd string
     */
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd")

    @Test
    fun testProvideMarsRoverApi() {
        MarsRoverModule.provideMarsRoverApi()
    }

    @Test
    fun testGetMarsRoverPhotos() {
        val api = MarsRoverModule.provideMarsRoverApi()
        val response = runBlocking {
            api.listPhotos()
        }
        val photos = response.photos
        assertFalse(photos.isEmpty())


        // these aren't great tests, but we can't fix the exact photos we get back.
        // testing that the values for photo 4477 are correct allows us to still validate our API,
        // even though this is quite a brittle test :(
        val photo = photos[0]
        assertEquals(4477, photo.id)

        // photo parameters
        assertEquals(1, photo.sol)
        assertEquals(
            "http://mars.jpl.nasa.gov/msl-raw-images/msss/00001/mcam/0001ML0000001000I1_DXXX.jpg",
            photo.imageUrl
        )
        assertEquals(dateFormat.parse("2012-08-07"), photo.earthDate)

        // camera parameters
        assertEquals(22, photo.camera.id)
        assertEquals("MAST", photo.camera.name)
        assertEquals(5, photo.camera.roverId)
        assertEquals("Mast Camera", photo.camera.fullName)

        // rover parameters
        assertEquals(5, photo.rover.id)
        assertEquals("Curiosity", photo.rover.name)
        assertEquals(dateFormat.parse("2012-08-06"), photo.rover.landingDate)
        assertEquals(dateFormat.parse("2011-11-26"), photo.rover.launchDate)
        assertEquals("active", photo.rover.status)
    }
}