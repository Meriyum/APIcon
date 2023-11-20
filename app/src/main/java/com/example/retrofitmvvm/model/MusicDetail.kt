package com.example.retrofitmvvm.model


/////
data class MusicDetail(
    val message: MessageDetail
)




data class MessageDetail(
    val header: HeaderDetail,
    val body: BodyDetail
)

data class HeaderDetail(
    val status_code: Int,
    val execute_time: Double
)

data class BodyDetail(
    val lyrics: Lyrics
)

data class Lyrics(
    val lyrics_id: Long,
    val explicit: Int,
    val lyrics_body: String,
    val script_tracking_url: String,
    val pixel_tracking_url: String,
    val lyrics_copyright: String,
    val updated_time: String
)
