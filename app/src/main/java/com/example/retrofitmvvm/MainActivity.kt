package com.example.retrofitmvvm

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitmvvm.api.ApiInterface
import com.example.retrofitmvvm.api.ApiUtilities
import com.example.retrofitmvvm.model.Track
import com.example.retrofitmvvm.viewmodel.SongsViewModel
import com.example.retrofitmvvm.repository.SongsRepository
import com.example.retrofitmvvm.ui.theme.RetrofitmvvmTheme
import com.example.retrofitmvvm.viewmodel.SongsViewModelFactory
import com.example.retrofitmvvm.model.MismatchResponse

class MainActivity : ComponentActivity() {

    private lateinit var songsViewModel: SongsViewModel

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetrofitmvvmTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val apiInterface=ApiUtilities.getInstance().create(ApiInterface::class.java)

                    val songsRepository=SongsRepository(apiInterface)
                    //if viewmodel is parameterised 1-viewmodelfactory is used to provide data to viewmodel.
                    songsViewModel= ViewModelProvider(this, SongsViewModelFactory(songsRepository)).get(SongsViewModel::class.java)


                    songsViewModel.song.observe(this) {
                        //   Log.d("naheed", "oncreate:${it.toString()}")
                        it.message.body.track_list.iterator().forEach { trackItem ->
                            Log.d(
                                "sara",
                                "name=${trackItem.track.artist_name}, track_id=${trackItem.track.track_id}"
                            )
                        }
                    }
                    ///////////////////////////////////

                    songsViewModel.songDetail.observe(this){song->
                        //   Log.d("naheed", "oncreate:${it.toString()}")

                            Log.d(
                                "hello",
                                "name=${song.toString()}"
                            )
                        }


                    ///////////////////////////////

                    LaunchedEffect(songsViewModel) {


                        songsViewModel.loadMemes()
                    }
                    songsViewModel.song.observe(this) { memes ->
                        setContent {
                            // Your Compose UI code here
                            MemesList(memes)
                        }
                    }
                }
            }
        }
    }

@Composable
fun MemesList(memes: MismatchResponse) {

    LazyColumn {
        items(memes?.message?.body?.track_list ?: emptyList()) { trackItem ->
            TrackItemshow(trackItem.track)

        }
    }
}
@Composable
fun TrackItemshow(track: Track) {
    var songdetail by remember { mutableStateOf(0) }
    var isChecked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier

            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = { isChecked = it },
                modifier = Modifier.padding(end = 16.dp)
            )
            Column {
                Text(text = "Name: ${track.artist_name}", style = TextStyle(fontSize = 18.sp))
                Text(
                    text = "Track ID: ${track.track_id}",
                    style = TextStyle(fontSize = 16.sp, color = Color.Gray)
                )
            }
        }
    }


    LaunchedEffect(isChecked) {
        if (isChecked) {
            Log.d("checkbox", "Checked Track ID: ${track.track_id}")
            songdetail = track.track_id
         //   songDetail = track.track_id // Update songDetail with the new value
            songsViewModel.loadMemesDetail(songdetail)
        }
}}}