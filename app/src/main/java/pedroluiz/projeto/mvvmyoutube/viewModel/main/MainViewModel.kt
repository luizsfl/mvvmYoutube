package pedroluiz.projeto.mvvmyoutube.viewModel.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pedroluiz.projeto.mvvmyoutube.Repository.MainRepository
import pedroluiz.projeto.mvvmyoutube.model.Live
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel constructor(private val repository: MainRepository):ViewModel() {

    val liveList = MutableLiveData<List<Live>>()
    val erroMessenger = MutableLiveData<String>()


    fun getAllLives(){
        val request = repository.getAllLives()
        request.enqueue(object : Callback<List<Live>> {
            override fun onResponse(call: Call<List<Live>>, response: Response<List<Live>>) {
                //quando for um sucesso
                liveList.postValue(response.body())
            }

            override fun onFailure(call: Call<List<Live>>, t: Throwable) {
                erroMessenger.postValue("Erro"+t.message)
             }
        })
    }
}