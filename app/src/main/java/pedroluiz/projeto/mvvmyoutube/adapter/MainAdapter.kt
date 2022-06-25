import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import pedroluiz.projeto.mvvmyoutube.R
import pedroluiz.projeto.mvvmyoutube.databinding.ItemLiveBinding
import pedroluiz.projeto.mvvmyoutube.model.Live


class MainAdapter(private val onLinkClicked: (Live) -> Unit, private val onFavoteClicked: (Live) -> Unit)
    : RecyclerView.Adapter<MainViewHolder>() {

    private var lives = mutableListOf<Live>()

    fun setLiveList(lives: List<Live>) {
        this.lives = lives.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLiveBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val live = lives[position]
        holder.bind(live, onLinkClicked,onFavoteClicked)
    }

    override fun getItemCount(): Int {
        return lives.size
    }
}

class MainViewHolder(val binding: ItemLiveBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(live: Live, onLinkClicked: (Live) -> Unit,onFavoriteClicked: (Live) -> Unit) {

        binding.title.text = live.title
        binding.author.text = live.author

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)

        Glide.with(itemView.context)
            .applyDefaultRequestOptions(requestOptions)
            .load(live.thumbnailUrl)
            .into(binding.thumbnail)

        binding.thumbnail.setOnClickListener {
            onLinkClicked(live)
        }

        if (live.favorito == true) {
            binding.imvFavorite.setColorFilter(Color.RED)
        } else {
            binding.imvFavorite.setColorFilter(Color.BLACK)
        }

        binding.imvFavorite.setOnClickListener {

            ClickdFavorito(live, onFavoriteClicked)
        }
    }

    private fun ClickdFavorito(
        live: Live,
        onFavoriteClicked: (Live) -> Unit
    ) {
        live.favorito = !live.favorito

        if (live.favorito == true) {
            binding.imvFavorite.setColorFilter(Color.RED)
        } else {
            binding.imvFavorite.setColorFilter(Color.BLACK)
        }

        onFavoriteClicked(live)
    }

}