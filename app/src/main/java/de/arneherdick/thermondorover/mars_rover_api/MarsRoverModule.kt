package de.arneherdick.thermondorover.mars_rover_api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Provides an implementation of the MarsRoverApi to Hilt
 */
@Module
@InstallIn(ViewModelComponent::class)
object MarsRoverModule {

    /**
     * Provides an implementation of the MarsRoverApi to Hilt
     */
    @Provides
    fun provideMarsRoverApi(): MarsRoverApi = Retrofit.Builder()
        .baseUrl("https://api.nasa.gov/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MarsRoverApi::class.java)
}
