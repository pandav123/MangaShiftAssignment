package com.example.mangashelf.viewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangashelf.modal.MangaShelfEntity
import com.example.mangashelf.modal.MangaShelfItem
import com.example.mangashelf.useCase.MangaShelfUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MangaShelfViewModel @Inject constructor(
    private val mangaShelfUseCase: MangaShelfUseCase
): ViewModel() {

    private val _mangaList = MutableStateFlow<Map<Int, List<MangaShelfEntity>>>(emptyMap())
    val mangaList : StateFlow<Map<Int, List<MangaShelfEntity>>> = _mangaList
    @RequiresApi(Build.VERSION_CODES.O)
    fun getMangaList(){
        viewModelScope.launch{
            try {
                val response = mangaShelfUseCase.execute()
                _mangaList.value = response
            }
            catch (e: Exception){
                Log.d("Tag",e.message.toString())
            }
        }
    }
}