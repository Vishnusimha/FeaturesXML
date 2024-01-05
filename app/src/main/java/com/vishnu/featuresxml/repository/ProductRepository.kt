package com.vishnu.featuresxml.repository

import android.util.Log
import com.vishnu.featuresxml.data.AddProductRequest
import com.vishnu.featuresxml.data.AddProductResponse
import com.vishnu.featuresxml.data.Product
import retrofit2.Response
import javax.inject.Inject

class ProductRepository @Inject constructor(private val swipeApiService: SwipeApiService) {
    suspend fun getProducts(): List<Product>? {
        val response = swipeApiService.getProducts()
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    suspend fun addProduct(product: AddProductRequest): Response<AddProductResponse> {
        return try {
            Log.d("ProductRepository", "addProduct called")
            swipeApiService.addProduct(
                product.product_name,
                product.product_type,
                product.price,
                product.tax
            )
        } catch (e: Exception) {
            Log.d("ProductRepository", "Exception adding product: $e")
            Response.error(500, okhttp3.ResponseBody.create(null, "Check ProductRepository"))
        }
    }

}
