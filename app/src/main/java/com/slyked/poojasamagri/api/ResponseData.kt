package com.slyked.admin.api


sealed class ResponseData<T>(val data:T? = null,val errorMessage: String? = null)
{
   class Loading<T>: ResponseData<T>();
    class Successful<T>(data: T? = null):ResponseData<T>(data = data)
    class Error<T>(errorMessage: String? = null):ResponseData<T>(errorMessage = errorMessage)
}
