package com.example.mangashelf

import com.example.mangashelf.modal.MangaShelfItem
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.http.GET

interface MangaApiService {
    @GET("b/KEJO")
    suspend fun getManga () : List<MangaShelfItem>
}