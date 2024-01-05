package com.vishnu.featuresxml.viewmodel

import android.app.Application
import android.media.MediaRecorder
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class SpeechViewModel @Inject constructor(application: Application) : ViewModel() {

    //text to speech API impl
    private val _recognizedText = MutableLiveData<String>()
    val recognizedText: LiveData<String> get() = _recognizedText

    private val textToSpeechEngine: TextToSpeech by lazy {
        TextToSpeech(
//            context
            application
        ) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeechEngine.language = Locale.UK
            }
        }
    }

    fun handleSpeechRecognitionResult(result: ArrayList<String>?) {
        result?.let {
            val recognizedText = it.getOrNull(0)
            _recognizedText.value = recognizedText
        }
    }

    fun speakText(text: String) {
        if (text.isNotEmpty()) {
            textToSpeechEngine.speak(text, TextToSpeech.QUEUE_FLUSH, null, "tts1")
        }
    }

    override fun onCleared() {
        super.onCleared()
        textToSpeechEngine.shutdown()
    }
}