package fr.sbelhadj.animeandroid.presentation.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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


        Singletons.animeApi.getAnimeList().enqueue(object : Callback<AnimeListResponse>{
            override fun onFailure(call: Call<AnimeListResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(
                    call: Call<AnimeListResponse>,
                    listResponse: Response<AnimeListResponse>
            ) {
                if(listResponse.isSuccessful && listResponse.body()!= null){
                    val AnimeResponse = listResponse.body()!!
                    adapter.updateList(AnimeResponse.top)
                }
            }

        })

    }

  private fun onClickedAnime(anime: Anime) {
        findNavController().navigate(R.id.action_AnimeListFragment_to_AnimeDetailFragment)
    }
}
