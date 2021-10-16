package de.arneherdick.thermondorover.mars_rover_api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
object MarsRoverModule {
    @Provides
    fun provideMarsRoverApi(): MarsRoverApi = Retrofit.Builder()
        .baseUrl("https://api.nasa.gov/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MarsRoverApi::class.java)
}