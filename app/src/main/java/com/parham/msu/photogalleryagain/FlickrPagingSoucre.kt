package com.parham.msu.photogalleryagain

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.parham.msu.photogalleryagain.api.FlickrApi
import com.parham.msu.photogalleryagain.api.GalleryItem

class FlickrPagingSource(
    private val flickrApi: FlickrApi
) : PagingSource<Int, GalleryItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GalleryItem> {
        val position = params.key ?: 1
        return try {
            val response = flickrApi.fetchPhotos(page = position, perPage = params.loadSize)
            LoadResult.Page(
                data = response.photos.galleryItems,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (response.photos.galleryItems.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GalleryItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
