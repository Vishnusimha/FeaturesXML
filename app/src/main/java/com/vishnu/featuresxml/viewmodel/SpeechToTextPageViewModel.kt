package com.vishnu.featuresxml.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.media.MediaRecorder
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SpeechToTextPageViewModel @Inject constructor(application: Application) : ViewModel() {
    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var mediaRecorder: MediaRecorder

    private val _recognitionResult = MutableLiveData<String>()
    val recognitionResult: LiveData<String> get() = _recognitionResult

    fun setupSpeechRecognition(context: Context) {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
        speechRecognizer.setRecognitionListener(SpeechRecognitionListener())

        mediaRecorder = MediaRecorder()
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        mediaRecorder.setOutputFile("/dev/null")
    }

    fun startSpeechRecognition() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "com.example.package.name")
        intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)

        speechRecognizer.startListening(intent)
    }

    fun onDestroy() {
        speechRecognizer.destroy()
        mediaRecorder.release()
    }

    private inner class SpeechRecognitionListener : RecognitionListener {
        override fun onReadyForSpeech(params: Bundle?) {}

        override fun onBeginningOfSpeech() {}

        override fun onRmsChanged(rmsdB: Float) {}

        override fun onBufferReceived(buffer: ByteArray?) {}

        override fun onEndOfSpeech() {}

        override fun onError(error: Int) {
            _recognitionResult.value = "Error: $error"
        }

        override fun onPartialResults(partialResults: Bundle?) {
            val result = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            if (!result.isNullOrEmpty()) {
                _recognitionResult.value = result[0]
            }
        }

        override fun onResults(results: Bundle?) {
            val result = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            if (!result.isNullOrEmpty()) {
                _recognitionResult.value = result[0]
            }
        }

        override fun onEvent(eventType: Int, params: Bundle?) {}
    }
}
