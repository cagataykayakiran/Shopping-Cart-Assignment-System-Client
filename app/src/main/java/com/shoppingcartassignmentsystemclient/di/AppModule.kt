package com.shoppingcartassignmentsystemclient.di

import android.content.Context
import androidx.room.Room
import com.shoppingcartassignmentsystemclient.data.local.AppDatabase
import com.shoppingcartassignmentsystemclient.data.local.dao.CartDao
import com.shoppingcartassignmentsystemclient.data.local.dao.ProductDao
import com.shoppingcartassignmentsystemclient.data.repository.CartRepositoryImpl
import com.shoppingcartassignmentsystemclient.data.repository.ProductRepositoryImpl
import com.shoppingcartassignmentsystemclient.domain.repository.CartRepository
import com.shoppingcartassignmentsystemclient.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

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
    fun provideProductRepository(productDao: ProductDao): ProductRepository {
        return ProductRepositoryImpl(productDao)
    }

    @Provides
    @Singleton
    fun provideCartRepository(cartDao: CartDao): CartRepository {
        return CartRepositoryImpl(cartDao)
    }

}