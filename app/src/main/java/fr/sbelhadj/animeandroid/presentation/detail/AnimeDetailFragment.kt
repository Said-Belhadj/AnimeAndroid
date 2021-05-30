package fr.sbelhadj.animeandroid.presentation.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import fr.sbelhadj.animeandroid.R
import fr.sbelhadj.animeandroid.presentation.Singletons
import fr.sbelhadj.animeandroid.presentation.api.AnimeDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AnimeDetailFragment : Fragment() {

    private lateinit var textViewRank : TextView
    private lateinit var textViewScore : TextView
    private lateinit var textViewSynopsis : TextView
    private lateinit var textViewTitle : TextView
    private var textViewEpisodes : TextView? = null
    private lateinit var imgView : ImageView

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_anime_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textViewRank = view.findViewById(R.id.rank)
        textViewScore = view.findViewById(R.id.score)
        textViewTitle = view.findViewById(R.id.anime_title)
        textViewSynopsis = view.findViewById(R.id.synopsis)
        textViewEpisodes = view.findViewById(R.id.episodes)
        callApi()

        view.findViewById<FloatingActionButton>(R.id.back_button).setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    private fun callApi(){
        val mal_id = arguments?.get("animeId").toString()
        Singletons.animeApi.getAnimeDetail(mal_id).enqueue(object : Callback<AnimeDetailResponse>{
            override fun onFailure(call: Call<AnimeDetailResponse>, t: Throwable) {
            }

            override fun onResponse(call: Call<AnimeDetailResponse>, response: Response<AnimeDetailResponse>) {
                if(response.isSuccessful && response.body()!= null){
                    textViewRank.text = response.body()!!.rank.toString()
                    textViewScore.text = response.body()!!.score.toString()
                    textViewSynopsis.text = response.body()!!.synopsis
                    textViewTitle.text = response.body()!!.title
                    imgView = view?.findViewById(R.id.anime_img)!!
                    Glide
                        .with(imgView)
                        .load(response.body()!!.image_url)
                        .into(imgView)

                }
            }

        })
    }
}