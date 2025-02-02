package com.example.mangashelf

import com.example.mangashelf.modal.MangaShelfItem
import kotlinx.coroutines.flow.Flow


class MangaShelfRepo(
    private val mangaApiService: MangaApiService
) {
    suspend fun fetchMangaList () : List<MangaShelfItem> {
        return mangaApiService.getManga()
    }
}