package com.example.mangashelf.useCase

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.mangashelf.MangaShelfRepo
import com.example.mangashelf.mapper.MangaShelfMapper
import com.example.mangashelf.modal.MangaShelfEntity
import javax.inject.Inject

class MangaShelfUseCase @Inject constructor(
    private val mangaShelfRepo: MangaShelfRepo,
    private val mangaShelfMapper: MangaShelfMapper
){
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun execute() : Map<Int, List<MangaShelfEntity>> {
        return mangaShelfMapper.mapToEntity(mangaShelfRepo.fetchMangaList())
    }
}