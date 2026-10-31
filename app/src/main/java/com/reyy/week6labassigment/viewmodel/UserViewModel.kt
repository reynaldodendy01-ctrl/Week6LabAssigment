package com.reyy.week6labassigment.viewmodel
// UserViewModel.kt
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserViewModel : ViewModel() {
    // _uiState bersifat private, hanya bisa diubah dari dalam ViewModel
    private val _userName = MutableStateFlow("Tamu")

    // userName bersifat public (read-only), diobservasi oleh View
    val userName: StateFlow<String> = _userName.asStateFlow()

    // Fungsi untuk mengubah data (Action dari View)
    fun updateName(newName: String) {
        _userName.value = newName
    }
}