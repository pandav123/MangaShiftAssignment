package com.example.mangashelf.sharedPref

import android.content.Context
import android.content.SharedPreferences

class PreferencesHelper(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)

    fun saveFavoriteManga(id: String) {
        val favorites = getFavoriteManga().toMutableSet()
        if (favorites.contains(id)) {
            favorites.remove(id)
        } else {
            favorites.add(id)
        }
        sharedPreferences.edit().putString("favorites", favorites.joinToString(",")).apply()
    }

    private fun getFavoriteManga(): Set<String> {
        val savedString = sharedPreferences.getString("favorites", "") ?: ""
        return savedString.split(",").filter { it.isNotEmpty() }.toSet()
    }

    fun isMangaFavorite(id: String): Boolean {
        return getFavoriteManga().contains(id)
    }
}
