package com.example.retrofitmvvm.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.retrofitmvvm.api.ApiInterface

import com.example.retrofitmvvm.model.MismatchResponse
import com.example.retrofitmvvm.model.MusicDetail

class SongsRepository(private val apiInterface: ApiInterface) {
    //mutablelivedata means data type that can note and broadcast changes wherever they are attached with variable so we don't have to set them.
private val songsLiveData=MutableLiveData<MismatchResponse>()
    /////////////////////
    private val songsLiveDataDetail=MutableLiveData<MusicDetail>()
//    private val songsdetailLiveData=MutableLiveData<MusicDetailResponse>()
    private val API_KEY = "559f7e68ee59459369e4403ef24fd978"
    val song :LiveData<MismatchResponse>
        get()=songsLiveData
    val songdetail :LiveData<MusicDetail>
        get()=songsLiveDataDetail
    suspend fun getSongs(){
        val result=apiInterface.getTracks("sorry","You gotta go", API_KEY)
        if(result.body()!=null){
            songsLiveData.postValue(result.body())
        }
    }
    suspend fun getDetailSongs(songDetail: Int)

    {    Log.d("rep","rep$${songDetail}")

        val result=apiInterface.getTracksDetail(songDetail, API_KEY)
        if(result.body()!=null){
            songsLiveDataDetail.postValue(result.body())}
    }
}