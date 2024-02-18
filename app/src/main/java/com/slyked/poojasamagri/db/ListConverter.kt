package com.slyked.poojasamagri.db


import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.slyked.admin.product.model.ProductsVariant

class ListConverter {
    inline fun <reified T> Gson.fromJson(json: String) =
        fromJson<T>(json, object : TypeToken<T>() {}.type)
    @TypeConverter
    fun fromStringArrayList(value: List<ProductsVariant>): String {

        return Gson().toJson(value)
    }

    @TypeConverter
    fun toStringArrayList(value: String): List<ProductsVariant> {
        return try {
            Gson().fromJson<List<ProductsVariant>>(value) //using extension function
        } catch (e: Exception) {
            arrayListOf()
        }
    }

    @TypeConverter
    fun fromStringObject(value: ProductsVariant?): String {

        return Gson().toJson(value)
    }
    @TypeConverter
    fun toStringFromObject(value: String): ProductsVariant? {
        return try {
            Gson().fromJson<ProductsVariant?>(value) //using extension function
        } catch (e: Exception) {
            return null
        }

    }



}