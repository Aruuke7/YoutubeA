package kg.geektech.youtubea.remote

import kg.geektech.youtubea.models.Playlist
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("playlists")
    fun getPlaylist(
        @Query ("part") part:String,
        @Query ("channelId") channelId:String,
        @Query ("key") apiKey:String,
        @Query ("maxResults") maxResults:Int,

    ): Call<Playlist>

}