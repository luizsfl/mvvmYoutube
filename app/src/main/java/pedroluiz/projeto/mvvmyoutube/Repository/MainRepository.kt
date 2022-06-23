package pedroluiz.projeto.mvvmyoutube.Repository

import android.content.Context
import androidx.room.Room
import pedroluiz.projeto.mvvmyoutube.data.local.LiveDao
import pedroluiz.projeto.mvvmyoutube.data.local.LiveDataBase
import pedroluiz.projeto.mvvmyoutube.data.remoto.rest.RetrofitService
import pedroluiz.projeto.mvvmyoutube.model.Live

class MainRepository constructor(private val retrofitService: RetrofitService) {

    private lateinit var liveDataBase : LiveDataBase
    private lateinit var liveDao : LiveDao

    fun getAllLives() = retrofitService.getAllLives()


    fun inserirLive(live:Live, context: Context){
        liveDataBase = LiveDataBase.getInstance(context)
        liveDao = this.liveDataBase.liveDao()
        liveDao.inserirLive(live)
    }
}

