package com.example.mangashelf.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.mangashelf.modal.MangaShelfEntity
import com.example.mangashelf.sharedPref.PreferencesHelper

@Composable
fun MangaShelfScreen(
    mangaList: Map<Int, List<MangaShelfEntity>>,
    itemClicked :(MangaShelfEntity) -> Unit
) {
    var selectedYear by remember { mutableStateOf<Int?>(null) }
    val isLoading = remember { mutableStateOf(true) }
    val favorites = remember { mutableStateMapOf<String, Boolean>() }

    LaunchedEffect(mangaList) {
        if (selectedYear == null && mangaList.isNotEmpty()) {
            selectedYear = mangaList.keys.first()
            isLoading.value = false
        }
    }

    if (isLoading.value) {
        Loader()
    } else {
        Column {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(mangaList.keys.toList()) { year ->
                    TextButton(
                        onClick = { selectedYear = year },
                        colors = ButtonDefaults.textButtonColors(
                            containerColor = if (year == selectedYear) Color.Blue else Color.LightGray,
                            contentColor = Color.White
                        ),
                        modifier = Modifier.padding(horizontal = 4.dp)
                    ) {
                        Text(text = year.toString())
                    }
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                mangaList[selectedYear]?.let { mangaShelf ->
                    items(mangaShelf.size) { index ->
                        MangaItem(mangaShelf[index],favorites,itemClicked)
                    }
                }
            }
        }
    }
}


@Composable
fun MangaItem(
    manga: MangaShelfEntity,
    favorites: MutableMap<String, Boolean>,
    itemClicked :((MangaShelfEntity) -> Unit)? = null,
    route : String ? = null
) {

    val context = LocalContext.current
    val preferencesHelper = remember { PreferencesHelper(context) }
    val isFavorite = favorites.getOrDefault(manga.id, preferencesHelper.isMangaFavorite(manga.id))

    Box (
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.LightGray, shape = RoundedCornerShape(16.dp))
            .clickable {
                itemClicked?.invoke(manga)
            }
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
        ){
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.FillBounds,
                painter =  rememberAsyncImagePainter(manga.image),
                contentDescription = "cover_image"
            )
            Spacer(Modifier.height(4.dp))
            Column (
                modifier = Modifier
                    .padding(horizontal = 4.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "title : ${manga.title}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(2.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween

                ) {
                    Text(
                        text = "score : ${manga.score}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )
                    Text(
                        text = "popularity : ${manga.popularity}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )
                }
                Spacer(Modifier.height(2.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween

                ) {
                   val yop =  if(route == "detail"){
                       "published chapter date : ${manga.publishedChapterDate}"
                    }
                    else "year of publication : ${manga.publishedChapterYear}"
                    Text(
                        text = yop,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "favorite",
                        modifier = Modifier.clickable {
                            val newFavoriteState = !isFavorite
                            favorites[manga.id] = newFavoriteState
                            preferencesHelper.saveFavoriteManga(manga.id)
                        }
                    )
                }
                Spacer(Modifier.height(2.dp))
                if (route == "detail") {
                    Text(
                        text = "category : ${manga.category}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )
                }
            }

        }
    }
}

@Composable
fun Loader(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}