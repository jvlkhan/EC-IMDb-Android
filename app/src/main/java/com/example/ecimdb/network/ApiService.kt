package com.example.ecimdb.network

import com.example.ecimdb.model.PostModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("/9ba3dce1393320a78949")
    fun getPosts(): Call<MutableList<PostModel>>
}