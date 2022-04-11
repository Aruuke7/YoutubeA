package kg.geektech.youtubea.ui.detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.geektech.youtubea.databinding.ItemDetailBinding
import kg.geektech.youtubea.extenssions.loadImage
import kg.geektech.youtubea.models.Items

class DetailAdapter(private val initClick: (id: Items) -> Unit) :
    RecyclerView.Adapter<DetailAdapter.ViewHolder>() {
    private var list = ArrayList<Items>()

    inner class ViewHolder(private val binding: ItemDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {


        @SuppressLint("SetTextI18n")
        fun bind(items: Items) {
            binding.ivIm.loadImage(items.snippet.thumbnails.high.url)
            binding.title.text = items.snippet.title
            itemView.setOnClickListener {
                initClick(items)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(playlist: ArrayList<Items>) {
        this.list = playlist
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

}