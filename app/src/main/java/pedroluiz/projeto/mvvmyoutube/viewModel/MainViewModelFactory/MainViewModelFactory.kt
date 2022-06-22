package pedroluiz.projeto.mvvmyoutube.viewModel.MainViewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pedroluiz.projeto.mvvmyoutube.Repository.MainRepository
import pedroluiz.projeto.mvvmyoutube.viewModel.main.MainViewModel

class MainViewModelFactory constructor(private val repository: MainRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}