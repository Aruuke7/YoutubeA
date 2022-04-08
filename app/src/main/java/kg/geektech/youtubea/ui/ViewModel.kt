package kg.geektech.youtubea.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kg.geektech.youtubea.`object`.Constant
import kg.geektech.youtubea.base.BaseViewModel
import kg.geektech.youtubea.common.Resource
import kg.geektech.youtubea.common.Resource.Companion.loading
import kg.geektech.youtubea.common.Resource.Companion.success
import kg.geektech.youtubea.models.Playlist
import kg.geektech.youtubea.remote.ApiService
import kg.geektech.youtubea.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Response

class ViewModel:BaseViewModel() {
    private val apiService:ApiService by lazy {RetrofitClient.create() }

     fun getPlaylist(): MutableLiveData<Resource<Playlist>?> {
        val data = MutableLiveData<Resource<Playlist>?>()
        data.value = Resource.loading()

        apiService.getPlaylist(Constant.PART,Constant.CHANNEL_ID,Constant.API_KEY,Constant.MAX_RESULT).
        enqueue(object :retrofit2.Callback<Playlist>{
            override fun onResponse(call: Call<Playlist>, response: Response<Playlist>) {
               if (response.isSuccessful){
                   data.value = success(response.body())
               }
            }

            override fun onFailure(call: Call<Playlist>, t: Throwable) {
            }

        })
         return data
    }
}