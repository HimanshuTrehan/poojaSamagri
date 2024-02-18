package com.slyked.poojasamagri.products.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.slyked.admin.product.model.FavouriteProduct

@Dao
interface FavouriteProductDao {

    @Upsert(entity = FavouriteProduct::class)
    suspend fun addFavouriteProduct(productModel: FavouriteProduct)


    @Query("SELECT * FROM FavouriteTable")
   suspend fun getFavouriteProducts(): List<FavouriteProduct>

    @Query("DELETE FROM FavouriteTable WHERE id=:id ")
    suspend fun deleteFavouriteProduct(id:Int):Int

    @Query("SELECT EXISTS(SELECT * FROM FavouriteTable WHERE id = :id)")
    fun isItemExist(id : String) : Boolean

}