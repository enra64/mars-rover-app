package de.arneherdick.thermondorover.photo_list.ui

import de.arneherdick.thermondorover.getMarsPhoto
import de.arneherdick.thermondorover.mars_rover_api.models.Rover
import org.junit.Assert.*
import org.junit.Test
import java.util.*

class MarsPhotoDiffUtilTest {
    val diffUtil = MarsPhotoDiffUtil()

    @Test
    fun areItemsTheSame() {
        assertTrue(diffUtil.areItemsTheSame(getMarsPhoto(photoId = 1), getMarsPhoto(photoId = 1)))
        assertFalse(diffUtil.areItemsTheSame(getMarsPhoto(photoId = 1), getMarsPhoto(photoId = 2)))
    }

    @Test
    fun areContentsTheSame() {
        assertFalse(
            diffUtil.areContentsTheSame(
                getMarsPhoto(photoId = 1),
                getMarsPhoto(photoId = 2)
            )
        )
        assertTrue(
            diffUtil.areContentsTheSame(
                getMarsPhoto(photoId = 1),
                getMarsPhoto(photoId = 1)
            )
        )

        assertFalse(
            diffUtil.areContentsTheSame(
                getMarsPhoto(photoId = 1),
                getMarsPhoto(photoId = 1, sol = 2)
            )
        )

        assertFalse(
            diffUtil.areContentsTheSame(
                getMarsPhoto(photoId = 1),
                getMarsPhoto(photoId = 1, rover = Rover(1, "", Date(), Date(), "ok"))
            )
        )
    }
}
