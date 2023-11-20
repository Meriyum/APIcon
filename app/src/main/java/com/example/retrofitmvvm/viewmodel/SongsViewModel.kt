package com.example.retrofitmvvm.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.retrofitmvvm.model.MismatchResponse
import com.example.retrofitmvvm.model.MusicDetail
import com.example.retrofitmvvm.repository.SongsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.reflect.Array.get

class SongsViewModel(private val songsRepository: SongsRepository):ViewModel() {



    init{
    viewModelScope.launch (Dispatchers.IO){
        songsRepository.getSongs()
       
    }

}
    fun loadMemes() {
        viewModelScope.launch(Dispatchers.IO) {
            songsRepository.getSongs()
        }
    }
    fun loadMemesDetail(songDetail: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("view","trgid$${songDetail}")
            songsRepository.getDetailSongs(songDetail)
        }
    }

    val song:LiveData<MismatchResponse>
        get()=songsRepository.song
    val songDetail:LiveData<MusicDetail>
        get()=songsRepository.songdetail
   
}