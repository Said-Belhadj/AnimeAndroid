package fr.sbelhadj.animeandroid.presentation.api


data class AnimeDetailResponse (
        val rank : Int,
        val image_url : String,
        val title : String,
        val score : Float,
        val synopsis : String
)