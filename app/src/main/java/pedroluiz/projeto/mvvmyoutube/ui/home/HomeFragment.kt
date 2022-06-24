package pedroluiz.projeto.mvvmyoutube.ui.home

import MainAdapter
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
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


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
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
            adapter.setLiveList(lives)
        })

        viewModel.erroMessenger.observe(this, Observer { erro->
            Toast.makeText(requireContext(),erro, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllLives()
    }


    private fun telaYoutube(live: Live) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(live.link)
        startActivity(intent)
    }

    private fun salvaLive(live: Live) {
        AsyncTask.execute(Runnable {
          //  viewModel.inserirLive(live,this)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}