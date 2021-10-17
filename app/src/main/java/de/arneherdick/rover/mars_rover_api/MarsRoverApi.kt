package de.arneherdick.rover.mars_rover_api

import de.arneherdick.rover.mars_rover_api.models.MarsPhotoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Describes a subsection of the mars rover API from NASA
 */
interface MarsRoverApi {
    /**
     * Get a list of photos for a specific sol from mars for a specific rover.
     */
    @GET("mars-photos/api/v1/rovers/{rover}/photos")
    suspend fun listPhotos(
        /**
         * Which rover took the picture
         */
        @Path("rover") rover: String = "curiosity",

        /**
         * Which pagination page to get
         */
        @Query("page") page: Int = 1,

        /**
         * From which sol (i.e., number of mars "days" after landing)
         */
        @Query("sol") sol: Int = 1000,

        /**
         * API key for NASA
         */
        @Query("api_key") apiKey: String = "DEMO_KEY"
    ): MarsPhotoResponse
}
