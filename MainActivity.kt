package com.example.mangashelf

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mangashelf.modal.MangaShelfEntity
import com.example.mangashelf.screens.MangaDetailScreen
import com.example.mangashelf.screens.MangaShelfScreen
import com.example.mangashelf.ui.theme.MangaShelfTheme
import com.example.mangashelf.viewModel.MangaShelfViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MangaShelfViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getMangaList()

        setContent {
            val navController = rememberNavController()

            MangaShelfTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        NavHost(
                            navController = navController,
                            startDestination = "mangaShelf"
                        ) {
                            composable("mangaShelf") {
                                val mangaList = viewModel.mangaList.collectAsState().value
                                MangaShelfScreen(
                                    mangaList = mangaList,
                                    itemClicked = { manga ->
                                        navController.currentBackStackEntry?.savedStateHandle?.set("manga", manga)
                                        navController.navigate("mangaDetail")
                                    }
                                )
                            }

                            composable("mangaDetail") {
                                val manga = navController.previousBackStackEntry?.savedStateHandle?.get<MangaShelfEntity>("manga")
                                if (manga != null) {
                                    MangaDetailScreen(manga = manga)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
