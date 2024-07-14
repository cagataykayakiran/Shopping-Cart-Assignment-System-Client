package com.shoppingcartassignmentsystemclient.di

import android.content.Context
import androidx.room.Room
import com.shoppingcartassignmentsystemclient.data.local.AppDatabase
import com.shoppingcartassignmentsystemclient.data.local.dao.CartDao
import com.shoppingcartassignmentsystemclient.data.local.dao.ProductDao
import com.shoppingcartassignmentsystemclient.data.remote.ApiCall
import com.shoppingcartassignmentsystemclient.data.repository.ProductRepositoryImpl
import com.shoppingcartassignmentsystemclient.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    @Provides
    @Singleton
    fun provideApi(): ApiCall {
        return Retrofit.Builder()
            .baseUrl("")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiCall::class.java)
    }

    @Provides
    @Singleton
    fun provideRecipeDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            klass = AppDatabase::class.java,
            name = "cart.db",
        ).build()
    }

    @Provides
    @Singleton
    fun provideProductDao(database: AppDatabase): ProductDao {
        return database.productDao()
    }

    @Provides
    @Singleton
    fun provideCartDao(database: AppDatabase): CartDao {
        return database.cartDao()
    }

    @Provides
    @Singleton
    fun provideRepository(productDao: ProductDao): ProductRepository {
        return ProductRepositoryImpl(productDao)
    }
}