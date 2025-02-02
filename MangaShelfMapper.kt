package com.example.mangashelf.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.mangashelf.modal.MangaShelfEntity
import com.example.mangashelf.modal.MangaShelfItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

class MangaShelfMapper @Inject constructor() {
    @RequiresApi(Build.VERSION_CODES.O)
    fun mapToEntity(fetchMangaListResponse: List<MangaShelfItem>): Map<Int, List<MangaShelfEntity>> {
        return fetchMangaListResponse.map {
            MangaShelfEntity(
                id = it.id.orEmpty(),
                image = it.image.orEmpty(),
                score = it.score ?: 0.0,
                popularity = it.popularity ?: 0,
                title = it.title.orEmpty(),
                publishedChapterYear = it.publishedChapterDate?.let { it1 ->
                    convertTimestampToYear(
                        it1
                    )
                }?: 0,
                category = it.category.orEmpty(),
                publishedChapterDate = it.publishedChapterDate?.let { it1 ->
                    convertTimestampToDate(
                        it1
                    )
                }.orEmpty()
            )
        }
            .sortedBy { it.publishedChapterYear }
            .groupBy { it.publishedChapterYear }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertTimestampToYear(timestamp: Long): Int {
        val instant = Instant.ofEpochSecond(timestamp)
        val formatter = DateTimeFormatter.ofPattern("yyyy", Locale.getDefault())
            .withZone(ZoneId.systemDefault())

        return formatter.format(instant).toInt()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertTimestampToDate(timestamp: Long): String {
        val instant = Instant.ofEpochSecond(timestamp)
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.getDefault())
            .withZone(ZoneId.systemDefault())

        return formatter.format(instant)
    }
}