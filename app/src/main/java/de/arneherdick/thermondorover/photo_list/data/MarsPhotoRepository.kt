package de.arneherdick.thermondorover.photo_list.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import de.arneherdick.thermondorover.mars_rover_api.MarsRoverApi
import de.arneherdick.thermondorover.mars_rover_api.models.MarsPhoto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * The MarsPhotoRepository handles retrieving a list of photos from the NASA Mars Rover photo api
 */
class MarsPhotoRepository @Inject constructor(
    /**
     * Implementation of the MarsRoverApi provided by retrofit
     */
    private val api: MarsRoverApi
) {
    /**
     * Get a flow of paging data of mars photos. The paging data is provided by the
     * MarsPhotoPagingSource and can be used to build an infinitely scrolling list of mars photos.
     */
    fun getPhotoStream(): Flow<PagingData<MarsPhoto>> {
        return Pager(
            config = PagingConfig(MARS_ROVER_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { MarsPhotoPagingSource(api) }
        ).flow
    }

    companion object {
        const val MARS_ROVER_PAGE_SIZE = 25
    }
}
