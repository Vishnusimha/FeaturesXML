package com.vishnu.featuresxml.di

import com.vishnu.featuresxml.repository.ProductRepository
import com.vishnu.featuresxml.repository.SwipeApiService
import com.vishnu.featuresxml.viewmodel.ProductViewModel
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
    private const val SWIPE_BASE_URL = "https://app.getswipe.in/api/public/"

    @Singleton
    @Provides
    fun provideSwipeApiService(): SwipeApiService {
        return Retrofit.Builder()
            .baseUrl(SWIPE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SwipeApiService::class.java)
    }

    @Provides
    fun provideProductRepository(swipeApiService: SwipeApiService): ProductRepository {
        return ProductRepository(swipeApiService)
    }

    @Provides
    fun provideProductViewModel(repository: ProductRepository): ProductViewModel {
        return ProductViewModel(repository)
    }
}
