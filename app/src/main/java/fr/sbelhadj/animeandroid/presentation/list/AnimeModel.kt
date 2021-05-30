package fr.sbelhadj.animeandroid.presentation.list

sealed class AnimeModel

data class AnimeSuccess(val animeList: List<Anime>) : AnimeModel()
object AnimeError : AnimeModel()
object AnimeLoader : AnimeModel()