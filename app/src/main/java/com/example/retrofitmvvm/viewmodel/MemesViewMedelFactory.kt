package com.example.retrofitmvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitmvvm.repository.SongsRepository

class SongsViewModelFactory(private val songsRepository: SongsRepository) : ViewModelProvider.Factory {
   override fun <T : ViewModel> create(modelClass: Class<T>): T {
      return SongsViewModel(songsRepository) as T
}}