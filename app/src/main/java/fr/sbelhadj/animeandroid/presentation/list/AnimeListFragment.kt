package fr.sbelhadj.animeandroid.presentation.list

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.sbelhadj.animeandroid.R
import fr.sbelhadj.animeandroid.presentation.Singletons
import fr.sbelhadj.animeandroid.presentation.api.AnimeListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class AnimeListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView;
    private val adapter = AnimeAdapter(listOf(), ::onClickedAnime)

    private val  layoutManager = LinearLayoutManager(context)

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_anime_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.anime_recyclerview)

        recyclerView.apply {
            layoutManager = this@AnimeListFragment.layoutManager
            adapter = this@AnimeListFragment.adapter
        }
        callApi()
        /*val list = getListFromCache()
        if (list.isEmpty()) {
            callApi()
        }else {
            showList(list)
        }*/

    }

    /*private fun getListFromCache(): List<Anime> {
    }*/

    /*private fun saveListIntoCache(animeList: List<Anime>) {

    }*/


    private fun callApi() {
        Singletons.animeApi.getAnimeList().enqueue(object : Callback<AnimeListResponse> {
            override fun onFailure(call: Call<AnimeListResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(
                call: Call<AnimeListResponse>,
                listResponse: Response<AnimeListResponse>
            ) {
                if (listResponse.isSuccessful && listResponse.body() != null) {
                    val animeResponse = listResponse.body()!!
                    //saveListIntoCache()
                    showList(animeResponse.top)
                }
            }

        })
    }

    private fun showList(animeList: List<Anime>) {
        adapter.updateList(animeList)
    }

    private fun onClickedAnime(anime: Anime) {
        val bundle = bundleOf("animeId" to anime.mal_id)
        findNavController().navigate(R.id.action_AnimeListFragment_to_AnimeDetailFragment, bundle)
    }
}
