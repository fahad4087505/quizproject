package com.example.a2by3_android.di

import com.example.a2by3_android.BuildConfig
import com.example.a2by3_android.apiservice.CreateCardApiService
import com.example.a2by3_android.apiservice.IncludeDetailsApiService
import com.example.a2by3_android.apiservice.RegisterUserApiService
import com.example.a2by3_android.apiservice.SellingProductListApiService
import com.example.a2by3_android.datasource.CreateCardDataSource
import com.example.a2by3_android.datasource.IncludeDetailsDataSource
import com.example.a2by3_android.datasource.RegisterUserDataSource
import com.example.a2by3_android.datasource.SellingProductListDataSource
import com.example.a2by3_android.network.NoInternetInterceptor
import com.example.a2by3_android.repository.CreateCardRepository
import com.example.a2by3_android.repository.IncludeDetailsRepository
import com.example.a2by3_android.repository.RegisterUserRepository
import com.example.a2by3_android.repository.SellingProductListRepository
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
    val client = OkHttpClient.Builder()
      .connectTimeout(30, TimeUnit.SECONDS)
      .writeTimeout(30, TimeUnit.SECONDS)
      .readTimeout(30, TimeUnit.SECONDS)
      .addInterceptor(interceptor)
      //.addInterceptor(BasicAuthInterceptor())
      .addInterceptor(NoInternetInterceptor())
      .build()
    return Retrofit.Builder()
      .baseUrl(BuildConfig.BASE_URL)
      .addConverterFactory(GsonConverterFactory.create(gson))
      .client(client)
      .build()
  }

  @Provides
  fun provideGson(): Gson = GsonBuilder().create()

  @Provides
  fun provideSellingProductsListApiService(retrofit: Retrofit): SellingProductListApiService = retrofit.create(
    SellingProductListApiService::class.java
  )

  @Singleton
  @Provides
  fun provideSellingProductsListRemoteResponse(sellingProductListApiService: SellingProductListApiService) =
    SellingProductListDataSource(sellingProductListApiService)


  @Singleton
  @Provides
  fun provideProductsRepository(sellingProductListDataSource: SellingProductListDataSource) =
    SellingProductListRepository(sellingProductListDataSource)
  @Provides
  fun provideIncludeDetailsApiService(retrofit: Retrofit): IncludeDetailsApiService = retrofit.create(
    IncludeDetailsApiService::class.java
  )

  @Singleton
  @Provides
  fun provideIncludeDetailsRemoteResponse(includeDetailsApiService: IncludeDetailsApiService) =
    IncludeDetailsDataSource(includeDetailsApiService)


  @Singleton
  @Provides
  fun provideIncludeDetailsRepository(includeDetailsDataSource: IncludeDetailsDataSource) =
    IncludeDetailsRepository(includeDetailsDataSource)

  @Provides
  fun createCardApiService(retrofit: Retrofit): CreateCardApiService = retrofit.create(CreateCardApiService::class.java)

  @Singleton
  @Provides
  fun provideCreateCardRemoteResponse(createCardApiService: CreateCardApiService) =
    CreateCardDataSource(createCardApiService)


  @Singleton
  @Provides
  fun provideCreateCardRepository(createCardDataSource: CreateCardDataSource) =
    CreateCardRepository(createCardDataSource)

  @Provides
  fun registerUserApiService(retrofit: Retrofit): RegisterUserApiService = retrofit.create(RegisterUserApiService::class.java)

  @Singleton
  @Provides
  fun provideRegisterUserResponse(registerUserApiService: RegisterUserApiService) =
    RegisterUserDataSource(registerUserApiService)


  @Singleton
  @Provides
  fun provideRegisterUserRepository(registerUserDataSource: RegisterUserDataSource) =
    RegisterUserRepository(registerUserDataSource)
}