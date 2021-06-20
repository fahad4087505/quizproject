package com.example.triviaquizapp.di
import com.example.triviaquizapp.BuildConfig
import com.example.triviaquizapp.apiservice.QuestionsApiService
import com.example.triviaquizapp.apiservice.CategoriesListApiService
import com.example.triviaquizapp.datasource.CategoriesListDataSource
import com.example.triviaquizapp.datasource.QuestionsDataSource
import com.example.triviaquizapp.network.NoInternetInterceptor
import com.example.triviaquizapp.repository.CategoriesListRepository
import com.example.triviaquizapp.repository.QuestionsRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
  @Singleton
  @Provides
  fun provideRetrofit(gson: Gson): Retrofit {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    val client = OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
      .writeTimeout(30, TimeUnit.SECONDS)
      .readTimeout(30, TimeUnit.SECONDS)
      .addInterceptor(interceptor)
      //.addInterceptor(BasicAuthInterceptor())
      .addInterceptor(NoInternetInterceptor()).build()
    return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).client(client).build()
  }

  @Provides
  fun provideGson(): Gson = GsonBuilder().create()

  @Provides
  fun provideSellingProductsListApiService(retrofit: Retrofit): CategoriesListApiService = retrofit.create(CategoriesListApiService::class.java)

  @Singleton
  @Provides
  fun provideSellingProductsListRemoteResponse(sellingProductListApiService: CategoriesListApiService) =
    CategoriesListDataSource(sellingProductListApiService)

  @Singleton
  @Provides
  fun provideProductsRepository(sellingProductListDataSource: CategoriesListDataSource) =
    CategoriesListRepository(sellingProductListDataSource)
//
  @Provides
  fun provideQuestionsApiService(retrofit: Retrofit): QuestionsApiService = retrofit.create(QuestionsApiService::class.java)

  @Singleton
  @Provides
  fun provideQuestionsRemoteResponse(questionsApiService: QuestionsApiService) =
    QuestionsDataSource(questionsApiService)

  @Singleton
  @Provides
  fun provideQuestionsRepository(questionsDataSource: QuestionsDataSource) =
    QuestionsRepository(questionsDataSource)
}