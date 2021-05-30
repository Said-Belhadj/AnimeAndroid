package fr.sbelhadj.animeandroid.presentation.list

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
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
    private lateinit var loader: ProgressBar;
    private lateinit var error: TextView;
    private val adapter = AnimeAdapter(listOf(), ::onClickedAnime)

    private val  layoutManager = LinearLayoutManager(context)

    private val viewModel: AnimeListViewModel by viewModels()

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
        loader = view.findViewById(R.id.anime_loader)
        error = view.findViewById(R.id.anime_error)

        recyclerView.apply {
            layoutManager = this@AnimeListFragment.layoutManager
            adapter = this@AnimeListFragment.adapter
        }
        viewModel.animeList.observe(viewLifecycleOwner, Observer { animeModel ->
            loader.isVisible = animeModel is AnimeLoader
            error.isVisible = animeModel is AnimeError
            if(animeModel is AnimeSuccess){
                adapter.updateList(animeModel.animeList)
            }
        })
    }


    private fun onClickedAnime(anime: Anime) {
        val bundle = bundleOf("animeId" to anime.mal_id)
        findNavController().navigate(R.id.action_AnimeListFragment_to_AnimeDetailFragment, bundle)
    }
}
