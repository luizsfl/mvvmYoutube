package pedroluiz.projeto.mvvmyoutube

import MainAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import pedroluiz.projeto.mvvmyoutube.Repository.MainRepository
import pedroluiz.projeto.mvvmyoutube.databinding.ActivityMainBinding
import pedroluiz.projeto.mvvmyoutube.rest.RetrofitService
import pedroluiz.projeto.mvvmyoutube.viewModel.MainViewModelFactory.MainViewModelFactory
import pedroluiz.projeto.mvvmyoutube.viewModel.main.MainViewModel

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var binding : ActivityMainBinding

    lateinit var viewModel : MainViewModel

    private val retrofitService = RetrofitService.getInstance()

    private val adapter = MainAdapter{}

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
}

