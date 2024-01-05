package com.vishnu.featuresxml.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.vishnu.featuresxml.databinding.ActivitySpeechToTextPageBinding
import com.vishnu.featuresxml.viewmodel.SpeechToTextPageViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val RECORD_AUDIO_PERMISSION_REQUEST_CODE = 1

@AndroidEntryPoint
class SpeechToTextPageActivity : AppCompatActivity() {

    private lateinit var _binding: ActivitySpeechToTextPageBinding
    private val viewModel: SpeechToTextPageViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySpeechToTextPageBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        // Check and request necessary permissions
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                RECORD_AUDIO_PERMISSION_REQUEST_CODE
            )
        } else {
//            setup
            setupSpeechRecognition()
        }

        viewModel.recognitionResult.observe(this, Observer {
            _binding.resultView.text = it
        })
    }

    private fun setupSpeechRecognition() {
        viewModel.setupSpeechRecognition(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }

    fun initiateVoiceToText(view: View) {
        viewModel.startSpeechRecognition()
    }
}