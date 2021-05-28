package fr.sbelhadj.animeandroid.presentation.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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

    private lateinit var textViewName : TextView

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_anime_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewName = view.findViewById(R.id.rank)
        callApi()
    }

    private fun callApi(){
        val mal_id = arguments?.get("animeId").toString()
        Singletons.animeApi.getAnimeDetail(mal_id).enqueue(object : Callback<AnimeDetailResponse>{
            override fun onFailure(call: Call<AnimeDetailResponse>, t: Throwable) {
            }

            override fun onResponse(call: Call<AnimeDetailResponse>, response: Response<AnimeDetailResponse>) {
                if(response.isSuccessful && response.body()!= null){
                    textViewName.text = response.body()!!.rank.toString()
                }
            }

        })
    }
}