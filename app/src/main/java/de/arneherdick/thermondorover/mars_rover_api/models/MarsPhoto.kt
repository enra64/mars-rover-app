package de.arneherdick.thermondorover.mars_rover_api.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.Date

/**
 * Encapsulates a single mars photo, including the day and sol of capture, its unique ID, and
 * information about the rover that took the pictures and the camera it used for the picture.
 */
@Parcelize
data class MarsPhoto(
    /**
     * Unique ID of this photo
     */
    val id: Int,

    /**
     * Sol (i.e. "mars day") when this picture was taken, counting up from 0 from the date of
     * landing
     */
    val sol: Int,

    /**
     * Camera that took this photo
     */
    val camera: RoverCamera,

    /**
     * URL where this photo can be retrieved
     */
    @SerializedName("img_src") val imageUrl: String,

    /**
     * Earth date corresponding to the datetime of photo capture.
     */
    @SerializedName("earth_date") val earthDate: Date,

    /**
     * Information about the rover that took this picture
     */
    val rover: Rover
) : Parcelable
