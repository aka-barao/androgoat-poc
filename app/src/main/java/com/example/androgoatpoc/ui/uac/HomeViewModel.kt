package com.example.androgoatpoc.ui.uac

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Unprotected Android Components"
    }
    val text: LiveData<String> = _text
}