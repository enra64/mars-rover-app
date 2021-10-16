package de.arneherdick.thermondorover.mars_rover_api.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

/**
 * Information about a mars rover that took photos.
 */
@Parcelize
data class Rover(
    /**
     * Unique ID for this rover
     */
    val id: Int,

    /**
     * Full name of this rover
     */
    val name: String,

    /**
     * Earth date of landing this rover on mars
     */
    @SerializedName("landing_date") val landingDate: Date,

    /**
     * Earth date of launching this rover from earth
     */
    @SerializedName("launch_date") val launchDate: Date,

    /**
     * Status, e.g., "active"
     */
    val status: String
) : Parcelable
