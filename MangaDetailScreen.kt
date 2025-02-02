package com.example.mangashelf.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mangashelf.modal.MangaShelfEntity

@Composable
fun MangaDetailScreen(
    manga: MangaShelfEntity
){
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ){
        val favorites = remember { mutableStateMapOf<String, Boolean>() }
        MangaItem(
            manga = manga,
            favorites = favorites,
            route = "detail"
        )
    }
}