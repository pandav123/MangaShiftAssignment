package com.example.mangashelf.DI

import com.example.mangashelf.MangaApiService
import com.example.mangashelf.MangaShelfRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://www.jsonkeeper.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): MangaApiService{
        return  retrofit.create(MangaApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRepo(apiService: MangaApiService): MangaShelfRepo{
        return MangaShelfRepo(apiService)
    }

}