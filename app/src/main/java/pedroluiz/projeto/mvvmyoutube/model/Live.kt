package pedroluiz.projeto.mvvmyoutube.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Live (
    @PrimaryKey
    var id :Int = 0,
    var title:String,
    var author:String,
    var thumbnailUrl:String,
    var link:String,
    var favorito:Boolean
)
