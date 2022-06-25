package pedroluiz.projeto.mvvmyoutube.ui.home

import MainAdapter
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import pedroluiz.projeto.mvvmyoutube.Repository.MainRepository
import pedroluiz.projeto.mvvmyoutube.data.remoto.rest.RetrofitService
import pedroluiz.projeto.mvvmyoutube.databinding.FragmentHomeBinding
import pedroluiz.projeto.mvvmyoutube.model.Live

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() =  _binding!!
    lateinit var viewModel : HomeViewModel
    private val retrofitService = RetrofitService.getInstance()

    private val adapter = MainAdapter(
        {live ->telaYoutube(live)}
        ,{live ->  salvaLive(live)}
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.recyclerview.layoutManager = LinearLayoutManager(context)

        viewModel = ViewModelProvider(this, HomeViewModelFactory(MainRepository(retrofitService))).get(
            HomeViewModel::class.java
        )

        binding.recyclerview.adapter = adapter

        return root

    }

    override fun onStart() {
        super.onStart()

        viewModel.liveList.observe(this, Observer { lives ->
            val livesFavoritos = favorito(lives)
            adapter.setLiveList(livesFavoritos)
        })

        viewModel.erroMessenger.observe(this, Observer { erro->
            Toast.makeText(requireContext(),erro, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onResume() {
        super.onResume()
        AsyncTask.execute(Runnable {
            viewModel.getAllLives(requireContext())
        })
    }


    private fun telaYoutube(live: Live) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(live.link)
        startActivity(intent)
    }

    private fun salvaLive(live: Live) {
        AsyncTask.execute(Runnable {
           viewModel.inserirLive(live,requireContext())
        })

    }

    private fun favorito(lives: List<Live>) :List<Live>{
        var listfavoritos  = lives
        AsyncTask.execute(Runnable {
            for (indice in listfavoritos.indices) {
                listfavoritos[indice].favorito = viewModel.isFavorito(listfavoritos[indice], requireContext())
            }
        })
    return listfavoritos
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}