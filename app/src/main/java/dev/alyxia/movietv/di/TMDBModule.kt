package dev.alyxia.movietv.di

import app.moviebase.tmdb.Tmdb3
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.alyxia.movietv.BuildConfig
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TMDBModule {
    @Singleton
    @Provides
    fun providesTmdbApi(): Tmdb3 {
        return Tmdb3(BuildConfig.TMDB_API_KEY)
    }
}