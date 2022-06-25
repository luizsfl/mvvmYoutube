package pedroluiz.projeto.mvvmyoutube.ui.favorito

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pedroluiz.projeto.mvvmyoutube.Repository.MainRepository
import pedroluiz.projeto.mvvmyoutube.data.local.LiveDao
import pedroluiz.projeto.mvvmyoutube.model.Live

class FavoritoViewModel constructor(private val repository: MainRepository):ViewModel() {

    val liveList = MutableLiveData<List<Live>>()
    val liveListAPI = MutableLiveData<List<Live>>()
    private lateinit var liveDao : LiveDao

    val erroMessenger = MutableLiveData<String>()

    fun getAllSearch(text:String?) {

        var newsSearch: MutableLiveData<List<Live>> = MutableLiveData<List<Live>>()
        liveList.value = liveListAPI.value
        if (liveList.value != null && text != null) {
            for (indice in liveList.value!!?.indices) {
                if (liveList.value!!.get(indice).title.uppercase().contains(text.uppercase())) {
                    newsSearch.value = newsSearch.value?.plus(liveList.value!!.get(indice)) ?: listOf(liveList.value!!.get(indice))
                }
            }

            if (newsSearch.value != null){
                liveList.value  = newsSearch.value
            }
            else {
                liveList.value =  mutableListOf()
            }

        }

    }

    fun inserirLive(live: Live, context: Context){
        repository.inserirLive(live,context)
        getAllFavorito(context)
    }

    fun getAllFavorito(context: Context){

        var listLivesFavorito = repository.getAAllFavorito(context)

            if (listLivesFavorito != null){
                liveList.postValue(listLivesFavorito)
            }
            else {
                liveList.postValue(mutableListOf())
            }
    }
}