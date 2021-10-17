package de.arneherdick.rover

import de.arneherdick.rover.mars_rover_api.models.MarsPhoto
import de.arneherdick.rover.mars_rover_api.models.Rover
import de.arneherdick.rover.mars_rover_api.models.RoverCamera
import java.text.SimpleDateFormat
import java.util.Date

/**
 * Utility function to easily get a default mars photo with some changeable properties.
 */
fun getMarsPhoto(
    photoId: Int = 1,
    sol: Int = 1,
    rover: Rover = Rover(1, "", getDate("2014-01-20"), getDate("2013-01-20"), "active")
): MarsPhoto {
    val roverCamera = RoverCamera(1, "", 1, "")
    return MarsPhoto(photoId, sol, roverCamera, "", getDate("2015-01-20"), rover)
}

/**
 * Use to quickly get a Date instance from a yyyy-MM-dd string
 */
fun getDate(date: String): Date {
    return SimpleDateFormat("yyyy-MM-dd").parse(date)!!
}
