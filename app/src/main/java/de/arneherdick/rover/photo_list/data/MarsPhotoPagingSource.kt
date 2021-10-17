package de.arneherdick.rover.photo_list.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import de.arneherdick.rover.mars_rover_api.MarsRoverApi
import de.arneherdick.rover.mars_rover_api.models.MarsPhoto
import de.arneherdick.rover.photo_list.data.MarsPhotoRepository.Companion.MARS_ROVER_PAGE_SIZE
import java.lang.Exception

/**
 * This photo paging source is responsible for accessing the paged NASA rover photo API.
 */
class MarsPhotoPagingSource(
    private val service: MarsRoverApi
) : PagingSource<Int, MarsPhoto>() {

    /**
     * Loads a single page, checks for errors, and provides hints for the next page.
     * If the next (or previous) page cannot be loaded, null is returned as the hint.
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MarsPhoto> {
        return load(params.key, params.loadSize)
    }

    /**
     * Loads a single page, checks for errors, and provides hints for the next page.
     * If the next (or previous) page cannot be loaded, null is returned as the hint.
     */
    suspend fun load(key: Int?, loadSize: Int): LoadResult<Int, MarsPhoto> {
        val page = key ?: PAGING_START_INDEX
        try {
            val photos = service.listPhotos(page = page).photos

            // do not allow backwards loading if we're on the first page
            val prevKey = if (page == PAGING_START_INDEX) {
                null
            } else {
                page - 1
            }
            // do not allow more loading if we're not getting the full 25 results
            val nextKey = if (photos.size < 25) {
                null
            } else {
                page + (loadSize / MARS_ROVER_PAGE_SIZE)
            }

            return LoadResult.Page(photos, prevKey, nextKey)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    /**
     * The returned refresh key is used for subsequent refresh calls to PagingSource.load
     * after the initial load.
     *
     * We need to get the previous key (or next key if previous is null) of the page
     * that was closest to the most recently accessed index. Anchor position is the most
     * recently accessed index.
     */
    override fun getRefreshKey(state: PagingState<Int, MarsPhoto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val PAGING_START_INDEX = 1
    }
}
