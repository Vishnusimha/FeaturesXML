package com.vishnu.featuresxml.presentation

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.vishnu.featuresxml.databinding.ActivitySpeechBinding
import com.vishnu.featuresxml.viewmodel.SpeechViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

private const val REQUEST_CODE_SPEECH_TO_TEXT = 1

@AndroidEntryPoint
class SpeechActivity : AppCompatActivity() {

    private val viewModel: SpeechViewModel by viewModels()
    private lateinit var _binding: ActivitySpeechBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySpeechBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        viewModel.recognizedText.observe(this, Observer { recognizedText ->
            _binding.speechTextView.text = recognizedText
        })
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_SPEECH_TO_TEXT -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    viewModel.handleSpeechRecognitionResult(result)
                }
            }
        }
    }

    fun speechToText(view: View) {
        val speechToTextIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        speechToTextIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        speechToTextIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        speechToTextIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now!")

        try {
            startActivityForResult(speechToTextIntent, REQUEST_CODE_SPEECH_TO_TEXT)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
            Toast.makeText(
                this,
                "Speech recognition not supported on this device",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun onTextToSpeech(view: View) {
        val text = _binding.speechTextView.text.toString().trim()
        viewModel.speakText(text)
    }

    fun clearResultText(view: View) {
        _binding.speechTextView.text = ""
    }

    fun goToClearView(view: View) {
        startActivity(Intent(this, SpeechToTextPageActivity::class.java))
    }
}