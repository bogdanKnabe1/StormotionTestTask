package com.ninpou.stormotiontesttask.di

import com.ninpou.stormotiontesttask.data.network.ApiClient
import com.ninpou.stormotiontesttask.data.network.SuggestionItemRemoteDataSource
import com.ninpou.stormotiontesttask.model.SuggestionItemRepository

// As i think really nice example of how DI is work, just an example, with out using dependencies HILT or Dagger itself.
// can be changed on HILT with just 2 annotations @HiltAndroidApp in Application, @AndroidEntryPoint in Activity or Fragment
// and @Module, @InstallIn in #DependencyInjectionTemplate, and @Provides at provideRepository fun!
object DependencyInjectionTemplate {

    private val dataSource = SuggestionItemRemoteDataSource(ApiClient)
    private val suggestionRepository = SuggestionItemRepository(dataSource)

    fun providerRepository() = suggestionRepository
}