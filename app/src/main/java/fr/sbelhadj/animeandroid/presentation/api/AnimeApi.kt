package fr.sbelhadj.animeandroid.presentation.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface AnimeApi {
    @GET("top/anime")
    fun getAnimeList(): Call<AnimeListResponse>

    @GET("anime/{mal_id}")
    fun getAnimeDetail(@Path("mal_id") id: String): Call<AnimeDetailResponse>

    @GET("/search/anime?q={title}")
    fun getAnimeResearch(@Path("title") title: String): Call<AnimeListResponse>

}

