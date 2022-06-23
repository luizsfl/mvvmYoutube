package pedroluiz.projeto.mvvmyoutube.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pedroluiz.projeto.mvvmyoutube.model.Live

@Dao
interface LiveDao {
    @Query("SELECT * FROM Live where favorito = :favorito")
    open fun getFavoritoLive(favorito: Boolean): LiveData<MutableList<Live>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserirLive(live:Live)

}