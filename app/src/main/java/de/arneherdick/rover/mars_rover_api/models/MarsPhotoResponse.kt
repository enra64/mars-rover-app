package de.arneherdick.rover.mars_rover_api.models

/**
 * Data container class for responses from the Mars Rover API
 */
data class MarsPhotoResponse(
    /**
     * The list of photos returned by the API
     */
    val photos: List<MarsPhoto>
)
