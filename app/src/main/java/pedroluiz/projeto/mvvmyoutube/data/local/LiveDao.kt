package pedroluiz.projeto.mvvmyoutube.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pedroluiz.projeto.mvvmyoutube.model.Live

@Dao
interface LiveDao {
    @Query("SELECT  * FROM Live where favorito = :favorito")
    fun getFavoritoLive(favorito:Boolean): MutableList<Live>

    @Query("SELECT count(*) FROM Live where favorito = :favorito and id=:id")
    fun isFavorito(id: Int,favorito :Boolean = true): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserirLive(live:Live)
}