package kg.geektech.youtubea.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.geektech.youtubea.databinding.ItemRecyclerBinding
import kg.geektech.youtubea.extenssions.loadImage
import kg.geektech.youtubea.models.Items

class PlaylistAdapter( private val initClick:(id:Items,title:String, desc:String,count:String)->Unit):
    RecyclerView.Adapter<PlaylistAdapter.ViewHolder>() {
    private var list= ArrayList<Items>()

   inner class ViewHolder(private val binding:ItemRecyclerBinding):RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(items: Items) {
            binding.ivIm.loadImage(items.snippet.thumbnails.default.url)
            binding.title.text = items.snippet.title
            val count = "${items.contentDetails.itemCount}"
            binding.desc.text = "$count video series"
            itemView.setOnClickListener{
                    initClick(items,items.snippet.title,items.snippet.description,binding.desc.text.toString())
            }
        }
   }


    @SuppressLint("NotifyDataSetChanged")
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