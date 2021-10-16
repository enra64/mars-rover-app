package de.arneherdick.thermondorover.mars_rover_api.models

import com.google.gson.annotations.SerializedName

/**
 * Information about a single rover camera.
 */
data class RoverCamera(
    /**
     * Unique ID for this camera
     */
    val id: Int,

    /**
     * Short name/identifier for this camera, e.g., MAST for Mast Camera
     */
    val name: String,

    /**
     * ID of the rover that took the photo
     */
    @SerializedName("rover_id") val roverId: Int,

    /**
     * Human-readable version of the camera's name
     */
    @SerializedName("full_name") val fullName: String
)
