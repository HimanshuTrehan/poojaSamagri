package com.slyked.poojasamagri.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.slyked.admin.product.model.CartProduct
import com.slyked.admin.product.model.FavouriteProduct
import com.slyked.admin.product.model.Product
import com.slyked.poojasamagri.products.dao.CartProductDao
import com.slyked.poojasamagri.products.dao.FavouriteProductDao

@Database(entities = [FavouriteProduct::class,CartProduct::class], version = 1)
@TypeConverters(ListConverter::class)
abstract class AppDataBase:RoomDatabase() {
    abstract fun getFavouriteProductDao(): FavouriteProductDao
    abstract fun getCartProductDao(): CartProductDao

    companion object {
        @Volatile
        private var INSTANCE:AppDataBase? = null
        fun getDatabase(context: Context):AppDataBase
        {
            if(INSTANCE==null)
            {
                synchronized(this)
                {
                    INSTANCE = setupDatabase(context)
                }
            }

            return INSTANCE!!
        }

        private fun setupDatabase(context: Context):AppDataBase {

            return Room.databaseBuilder(context,AppDataBase::class.java,"PoojaAppDB").build()

        }

    }

}