package pedroluiz.projeto.mvvmyoutube.ui.favorito

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pedroluiz.projeto.mvvmyoutube.Repository.MainRepository
import pedroluiz.projeto.mvvmyoutube.ui.home.HomeViewModel

class FavoritoViewModelFactory constructor(private val repository: MainRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(FavoritoViewModel::class.java)) {
            FavoritoViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModelFavorito Not Found")
        }
    }
}
