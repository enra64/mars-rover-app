package de.arneherdick.thermondorover.mars_rover_api

import de.arneherdick.thermondorover.getDate
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class MarsRoverApiTest {
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
        // testing that the values for a specific photos are correct allows us to still validate
        // our API, at least once...
        val photo = photos[0]

        if (photo.id == 4477) {
            // photo parameters
            assertEquals(1, photo.sol)
            assertEquals(
                "http://mars.jpl.nasa.gov/msl-raw-images/msss/00001/mcam/0001ML0000001000I1_DXXX.jpg",
                photo.imageUrl
            )
            assertEquals(getDate("2012-08-07"), photo.earthDate)

            // camera parameters
            assertEquals(22, photo.camera.id)
            assertEquals("MAST", photo.camera.name)
            assertEquals(5, photo.camera.roverId)
            assertEquals("Mast Camera", photo.camera.fullName)

            // rover parameters
            assertEquals(5, photo.rover.id)
            assertEquals("Curiosity", photo.rover.name)
            assertEquals(getDate("2012-08-06"), photo.rover.landingDate)
            assertEquals(getDate("2011-11-26"), photo.rover.launchDate)
            assertEquals("active", photo.rover.status)
        }
    }
}
