package pedroluiz.projeto.mvvmyoutube.Repository

import android.content.Context
import android.content.LocusId
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Room
import kotlinx.coroutines.flow.Flow
import pedroluiz.projeto.mvvmyoutube.data.local.LiveDao
import pedroluiz.projeto.mvvmyoutube.data.local.LiveDataBase
import pedroluiz.projeto.mvvmyoutube.data.remoto.rest.RetrofitService
import pedroluiz.projeto.mvvmyoutube.model.Live
import java.util.function.LongToDoubleFunction

class MainRepository constructor(private val retrofitService: RetrofitService) {

    private lateinit var liveDataBase : LiveDataBase
    private lateinit var liveDao : LiveDao

    fun getAllLives() = retrofitService.getAllLives()


    fun inserirLive(live:Live, context: Context){
        liveDataBase = LiveDataBase.getInstance(context)
        liveDao = this.liveDataBase.liveDao()
        liveDao.inserirLive(live)
    }

    fun getAAllFavorito(context: Context) : List<Live> {
        liveDataBase = LiveDataBase.getInstance(context)
        liveDao = this.liveDataBase.liveDao()
        var listaLive = liveDao.getFavoritoLive(true)
        return listaLive
    }

    fun isFavorito(id:Int,context: Context):Boolean{
        liveDataBase = LiveDataBase.getInstance(context)
        liveDao = this.liveDataBase.liveDao()
        var favorito = liveDao.isFavorito(id)>0
        return favorito
    }


}

