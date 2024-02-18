package com.slyked.poojasamagri.products.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.slyked.admin.product.model.CartProduct
import com.slyked.admin.product.model.FavouriteProduct

@Dao
interface CartProductDao {

    @Insert
    suspend fun addToCartProduct(productModel: CartProduct):Long


    @Query("SELECT * FROM CartTable")
   suspend fun getCartProducts(): List<CartProduct>

    @Query("DELETE FROM CartTable WHERE id=:id")
    suspend fun deleteCartProduct(id:Int):Int

    @Query("SELECT EXISTS(SELECT * FROM CartTable WHERE id = :id)")
    fun isItemExist(id : String) : Boolean



}