package pedroluiz.projeto.mvvmyoutube

import MainAdapter
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import pedroluiz.projeto.mvvmyoutube.Repository.MainRepository
import pedroluiz.projeto.mvvmyoutube.databinding.ActivityMainBinding
import pedroluiz.projeto.mvvmyoutube.model.Live
import pedroluiz.projeto.mvvmyoutube.rest.RetrofitService
import pedroluiz.projeto.mvvmyoutube.viewModel.MainViewModelFactory.MainViewModelFactory
import pedroluiz.projeto.mvvmyoutube.viewModel.main.MainViewModel
import java.net.URL


class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var binding : ActivityMainBinding

    lateinit var viewModel : MainViewModel

    private val retrofitService = RetrofitService.getInstance()

    private val adapter = MainAdapter(
         {live ->telaYoutube(live)}
        ,{live ->  salvaLive(live)}
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this,MainViewModelFactory(MainRepository(retrofitService))).get(
            MainViewModel::class.java
        )

        binding.recyclerview.adapter = adapter

    }

    override fun onStart() {
        super.onStart()
        viewModel.liveList.observe(this, Observer { lives ->
            adapter.setLiveList(lives)
        })

        viewModel.erroMessenger.observe(this, Observer { erro->
            Toast.makeText(this,erro,Toast.LENGTH_SHORT).show()
        })
    }

    override fun onResume() {
        super.onResume()

        viewModel.getAllLives()

    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_pesquisa,menu)
        val search = menu.findItem(R.id.menu_pesquisa).actionView as SearchView
        search.setOnQueryTextListener(this)
        return super.onPrepareOptionsMenu(menu)
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        viewModel.getAllSearch(newText)
        return false
    }


    private fun telaYoutube(live: Live) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(live.link)
        startActivity(intent)
    }
    private fun salvaLive(live: Live) {
        // binding.imvFavorite.setBackgroundColor(Color.BLACK)

    }
}

