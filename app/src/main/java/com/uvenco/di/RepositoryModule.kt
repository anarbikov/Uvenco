package com.uvenco.di

import com.uvenco.data.RepositoryImpl
import com.uvenco.domain.RepositoryInterface
import com.uvenco.room.DrinkDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepositoryImpl(
        drinkDao: DrinkDao
    ): RepositoryInterface = RepositoryImpl(drinkDao = drinkDao)

}