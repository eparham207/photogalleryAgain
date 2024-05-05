package com.parham.msu.photogalleryagain

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.parham.msu.photogalleryagain.api.FlickrApi
import com.parham.msu.photogalleryagain.api.GalleryItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


//New Code
class PhotoGalleryViewModel : ViewModel() {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.flickr.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
    private val flickrApi = retrofit.create(FlickrApi::class.java)
    private val photoRepository = PhotoRepository(flickrApi)

    val galleryItems = photoRepository.getSearchResultsStream().cachedIn(viewModelScope)
}