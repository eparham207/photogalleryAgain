package com.parham.msu.photogalleryagain

import com.parham.msu.photogalleryagain.api.FlickrApi
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create

class PhotoRepository {
    private val flickrApi: FlickrApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.flickr.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        flickrApi = retrofit.create()
    }
    suspend fun fetchPhotos() = flickrApi.fetchPhotos()
}