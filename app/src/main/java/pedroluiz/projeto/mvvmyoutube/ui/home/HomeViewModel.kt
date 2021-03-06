package pedroluiz.projeto.mvvmyoutube.ui.home

import android.content.Context
import android.text.BoringLayout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pedroluiz.projeto.mvvmyoutube.Repository.MainRepository
import pedroluiz.projeto.mvvmyoutube.data.local.LiveDao
import pedroluiz.projeto.mvvmyoutube.model.Live
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel constructor(private val repository: MainRepository) : ViewModel() {

    val liveList = MutableLiveData<List<Live>>()
    val liveListAPI = MutableLiveData<List<Live>>()
    private lateinit var liveDao: LiveDao

    val erroMessenger = MutableLiveData<String>()

    fun getAllLives(context: Context) {
        val request = repository.getAllLives()
        request.enqueue(object : Callback<List<Live>> {
            override fun onResponse(call: Call<List<Live>>, response: Response<List<Live>>) {
                //quando for um sucesso
                if (response.isSuccessful) {
                    var newsSearch: List<Live>? = response.body()

                    if (newsSearch != null) {
                        var novoId = 0
                        newsSearch?.map { live ->
                            novoId += 1
                            live.id = novoId
                        }
                    }

                    liveListAPI.postValue(newsSearch!!)

                    liveList.postValue(newsSearch!!)
                }
            }

            override fun onFailure(call: Call<List<Live>>, t: Throwable) {
                erroMessenger.postValue("Erro" + t.message)
            }
        })
    }

    fun getAllSearch(text: String?) {

        var newsSearch: MutableLiveData<List<Live>> = MutableLiveData<List<Live>>()
        liveList.value = liveListAPI.value
        if (liveList.value != null && text != null) {
            for (indice in liveList.value!!?.indices) {
                if (liveList.value!!.get(indice).title.uppercase().contains(text.uppercase())) {
                    newsSearch.value = newsSearch.value?.plus(liveList.value!!.get(indice))
                        ?: listOf(liveList.value!!.get(indice))
                }
            }

            if (newsSearch.value != null) {
                liveList.value = newsSearch.value
            } else {
                liveList.value = mutableListOf()
            }

        }

    }

    fun inserirLive(live: Live, context: Context) {
        repository.inserirLive(live, context)
    }

    fun isFavorito(live: Live,context: Context):Boolean {
        return repository.isFavorito(live.id,context )
    }



}