package kg.geektech.youtubea.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.geektech.youtubea.databinding.ItemRecyclerBinding
import kg.geektech.youtubea.extenssions.loadImage
import kg.geektech.youtubea.models.Items
import kg.geektech.youtubea.models.Playlist

class PlaylistAdapter( private val initClick:(id:String,title:String,desc:String,count:String)->Unit):
    RecyclerView.Adapter<PlaylistAdapter.ViewHolder>() {
    private var list= ArrayList<Items>()

   inner class ViewHolder(private val binding:ItemRecyclerBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(items: Items) {
            binding.ivIm.loadImage(items.snippet.thumbnails.high.url)
            binding.title.text = items.snippet.title
            val count = "${items.contentDetails.itemCount}"
            binding.desc.text = count
            itemView.setOnClickListener{
                items.snippet.title.let { it1-> items.snippet.description.let { it2->
                    initClick(items.id,it1,it2,items.contentDetails.itemCount.toString())
                } }
            }

        }

    }

    fun setList(playlist: ArrayList<Items>){
        this.list = playlist
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
       return list.size
    }
}