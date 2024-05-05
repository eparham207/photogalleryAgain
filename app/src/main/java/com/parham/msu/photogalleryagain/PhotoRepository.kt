package com.parham.msu.photogalleryagain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.parham.msu.photogalleryagain.api.FlickrApi
import com.parham.msu.photogalleryagain.api.GalleryItem
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class PhotoRepository(private val flickrApi: FlickrApi) {
    /**
     * The function getSearchResultsStream sets up a Pager object that handles data
     * loading in a paging fashion. It uses a PagingConfig to configure aspects of the
     * paging such as the page size and placeholders.
     *
     * @return A Flow of PagingData containing GalleryItems. This flow can be collected
     *         in the ViewModel and then observed in the UI to display paged data.
     */
    fun getSearchResultsStream(): Flow<PagingData<GalleryItem>> {
        // Create a Pager object that configures the paging setup
        return Pager(
            config = PagingConfig(
                pageSize = 20,                // Define number of items to load at once
                enablePlaceholders = false,   // No placeholders for unloaded items
                maxSize = 100,                // Optimal size of loaded data
                initialLoadSize = 40          // Number of items to load initially
            ),
            pagingSourceFactory = { FlickrPagingSource(flickrApi) }
        ).flow
    }
}