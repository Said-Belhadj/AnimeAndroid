package fr.sbelhadj.animeandroid.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.sbelhadj.animeandroid.presentation.Singletons
import fr.sbelhadj.animeandroid.presentation.api.AnimeListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AnimeListViewModel : ViewModel() {
    val animeList : MutableLiveData<AnimeModel> = MutableLiveData()

    init {
        callApi()
    }

    private fun callApi() {
        animeList.value = AnimeLoader

        Singletons.animeApi.getAnimeList().enqueue(object : Callback<AnimeListResponse> {
            override fun onFailure(call: Call<AnimeListResponse>, t: Throwable) {
               animeList.value = AnimeError
            }

            override fun onResponse(
                call: Call<AnimeListResponse>,
                listResponse: Response<AnimeListResponse>
            ) {
                if (listResponse.isSuccessful && listResponse.body() != null) {
                    val animeResponse = listResponse.body()!!
                    animeList.value = AnimeSuccess(animeResponse.top)
                } else {
                    animeList.value = AnimeError
                }
            }

        })
    }
}