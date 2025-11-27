package com.app.examen_tc2007b.di

import com.app.examen_tc2007b.data.local.CovidPreferences
import com.app.examen_tc2007b.data.remote.api.CovidApi
import com.app.examen_tc2007b.data.repository.CovidRepositoryImpl
import com.app.examen_tc2007b.domain.repository.CovidRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.api-ninjas.com/v1/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideCovidApi(retrofit: Retrofit): CovidApi {
        return retrofit.create(CovidApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCovidRepository(
        api: CovidApi,
        preferences: CovidPreferences,
        gson: Gson
    ): CovidRepository {
        return CovidRepositoryImpl(api, preferences, gson)
    }
}
