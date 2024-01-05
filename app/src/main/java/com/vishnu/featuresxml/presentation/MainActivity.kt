package com.vishnu.featuresxml.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.vishnu.featuresxml.data.AddProductRequest
import com.vishnu.featuresxml.databinding.ActivityMainBinding
import com.vishnu.featuresxml.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityMainBinding

    private val viewModel: ProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)
    }

    override fun onStart() {
        super.onStart()
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

    fun goToSpeechActivity(view: View) {
        startActivity(Intent(this, SpeechActivity::class.java))
    }
}
