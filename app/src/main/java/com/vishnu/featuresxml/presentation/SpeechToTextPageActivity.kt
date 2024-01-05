package com.vishnu.featuresxml.presentation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.vishnu.featuresxml.databinding.ActivitySpeechToTextPageBinding
import dagger.hilt.android.AndroidEntryPoint

private const val RECORD_AUDIO_PERMISSION_REQUEST_CODE = 1

@AndroidEntryPoint
class SpeechToTextPageActivity : AppCompatActivity() {
    private lateinit var _binding: ActivitySpeechToTextPageBinding

    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var mediaRecorder: MediaRecorder

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
            setupSpeechRecognition()
        }

        _binding.noPopUpButton.setOnClickListener {
            startSpeechRecognition()
        }
        speechRecognizer.setRecognitionListener(SpeechRecognitionListener())
    }

    //    @RequiresApi(Build.VERSION_CODES.S)
    private fun setupSpeechRecognition() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        speechRecognizer.setRecognitionListener(SpeechRecognitionListener())

        mediaRecorder = MediaRecorder()
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        mediaRecorder.setOutputFile("/dev/null")
    }

    private fun startSpeechRecognition() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, packageName)
        intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)

        speechRecognizer.startListening(intent)

        // For testing purposes, you can start recording here as well
//         mediaRecorder.start()
    }

    private inner class SpeechRecognitionListener : RecognitionListener {
        override fun onReadyForSpeech(params: Bundle?) {}

        override fun onBeginningOfSpeech() {}

        override fun onRmsChanged(rmsdB: Float) {}

        override fun onBufferReceived(buffer: ByteArray?) {}

        override fun onEndOfSpeech() {}

        override fun onError(error: Int) {
            _binding.resultView.text = "Error: $error"
        }

        override fun onPartialResults(partialResults: Bundle?) {
            val result = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            if (!result.isNullOrEmpty()) {
                _binding.resultView.text = result[0]
            }
        }

        override fun onResults(results: Bundle?) {
            val result = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            if (!result.isNullOrEmpty()) {
                _binding.resultView.text = result[0]
            }
        }

        override fun onEvent(eventType: Int, params: Bundle?) {}
    }

    override fun onDestroy() {
        super.onDestroy()
        speechRecognizer.destroy()
        mediaRecorder.release()
    }
}