package de.arneherdick.thermondorover.photo_list.data

import androidx.paging.PagingSource
import de.arneherdick.thermondorover.getMarsPhoto
import de.arneherdick.thermondorover.mars_rover_api.MarsRoverApi
import de.arneherdick.thermondorover.mars_rover_api.models.MarsPhoto
import de.arneherdick.thermondorover.mars_rover_api.models.MarsPhotoResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class MarsPhotoPagingSourceTest {

    @Test
    fun loadFirst() {
        val service = mockk<MarsRoverApi>()
        val photoResponse = MarsPhotoResponse(MutableList(25) { getMarsPhoto() })
        coEvery { service.listPhotos(allAny()) } returns photoResponse

        val pagingSource = MarsPhotoPagingSource(service)
        runBlocking {
            val result = pagingSource.load(1, 25) as PagingSource.LoadResult.Page<Int, MarsPhoto>
            assertEquals(photoResponse.photos, result.data)
            assertNull(result.prevKey)
            assertEquals(2, result.nextKey)
        }
    }

    @Test
    fun loadLast() {
        val service = mockk<MarsRoverApi>()
        val photoResponse = MarsPhotoResponse(emptyList())
        coEvery { service.listPhotos(allAny()) } returns photoResponse

        val pagingSource = MarsPhotoPagingSource(service)
        runBlocking {
            val rawResult = pagingSource.load(2, 25)
            val result = rawResult as PagingSource.LoadResult.Page<Int, MarsPhoto>
            assertEquals(photoResponse.photos, result.data)
            assertNull(result.nextKey)
            assertEquals(1, result.prevKey)
        }
    }

    @Test
    fun loadError() {
        val service = mockk<MarsRoverApi>()
        val error = Exception("A Network Error")
        coEvery { service.listPhotos(allAny()) } throws error

        val pagingSource = MarsPhotoPagingSource(service)
        runBlocking {
            val rawResult = pagingSource.load(2, 25)
            val result = rawResult as PagingSource.LoadResult.Error<Int, MarsPhoto>
            assertEquals(error, result.throwable)
        }
    }
}
