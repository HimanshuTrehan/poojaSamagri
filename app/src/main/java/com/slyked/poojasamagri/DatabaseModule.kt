package com.slyked.poojasamagri

import android.content.Context
import com.slyked.poojasamagri.db.AppDataBase
import com.slyked.poojasamagri.products.dao.CartProductDao
import com.slyked.poojasamagri.products.dao.FavouriteProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.security.AccessController.getContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object DatabaseModule {


    @Singleton
    @Provides
    fun getFavouriteDao(@ApplicationContext context: Context):FavouriteProductDao = AppDataBase.getDatabase(context).getFavouriteProductDao()

    @Singleton
    @Provides
    fun getCategoryDao(@ApplicationContext context: Context) :CartProductDao = AppDataBase.getDatabase(context).getCartProductDao()

}
