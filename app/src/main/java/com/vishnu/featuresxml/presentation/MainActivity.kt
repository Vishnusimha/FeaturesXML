package com.vishnu.featuresxml.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.vishnu.featuresxml.data.AddProductRequest
import com.vishnu.featuresxml.R
import com.vishnu.featuresxml.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: ProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    override fun onResume() {
        super.onResume()
        // Observe the StateFlow in the ViewModel
        lifecycleScope.launchWhenStarted {
            viewModel.products.collect { products ->
                // Update your RecyclerView adapter with the products
                Log.i("INFO", products.toString())
            }
        }

        // Fetch products when the activity is created
        viewModel.fetchProducts()

        viewModel.addProduct(
            AddProductRequest(
                product_name = "Ice Cream",
                product_type = "Food",
                price = "100.0",
                tax = "5.0"
            )
        )
    }
}
