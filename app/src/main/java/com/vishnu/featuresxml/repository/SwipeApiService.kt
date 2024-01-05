package com.vishnu.featuresxml.repository

import com.vishnu.featuresxml.data.AddProductResponse
import com.vishnu.featuresxml.data.Product
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface SwipeApiService {
    @GET("get")
    suspend fun getProducts(): Response<List<Product>>

    @FormUrlEncoded
    @POST("add")
    suspend fun addProduct(
        @Field("product_name") product_name: String,
        @Field("product_type") product_type: String,
        @Field("price") price: String,
        @Field("tax") tax: String
    ): Response<AddProductResponse>

}
