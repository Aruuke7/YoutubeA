package kg.geektech.youtubea

import android.content.Intent
import android.view.LayoutInflater
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import kg.geektech.youtubea.adapters.PlaylistAdapter
import kg.geektech.youtubea.base.BaseActivity
import kg.geektech.youtubea.common.NetworkConnection
import kg.geektech.youtubea.common.Status
import kg.geektech.youtubea.databinding.ActivityMainBinding
import kg.geektech.youtubea.ui.ViewModel

class MainActivity : BaseActivity<ViewModel, ActivityMainBinding>() {
    private var adapter= PlaylistAdapter(this::initClick)

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    private fun initClick(id: String, title: String, count: String,desc:String) {
        Intent(this,PlaylistDetailActivity::class.java).apply {
            putExtra(ID,id)
            putExtra(TITLE,title)
            putExtra(VIDEOS,count)
            putExtra(DESCRIPTION,desc)
            startActivity(this)
        }
    }

    override fun initListener() {
        super.initListener()
    }

    override fun checkInternet() {
        super.checkInternet()
        val connection = NetworkConnection(applicationContext)
        connection.observe(this){
                isConnected->
            if (isConnected){
                binding.apply {
                    recyclerView.visibility = VISIBLE
                    noInternetLayout.visibility = INVISIBLE
                }
            }else{
                binding.apply {
                    recyclerView.visibility = INVISIBLE
                    noInternetLayout.visibility = VISIBLE
                }
            }
        }
    }

    override fun initViewModel() {
        super.initViewModel()
        viewModel.getPlaylist().observe(this){
            if (it != null) {
                when(it.status){
                    Status.LOADING-> { binding.progressBar.visibility = VISIBLE }
                    Status.SUCCESS-> { binding.progressBar.visibility = INVISIBLE
                        binding.recyclerView.visibility = VISIBLE
                        it.data?.let { it1-> adapter.setList(it1.items) }}
                    Status.ERROR->{binding.progressBar.visibility = INVISIBLE
                        Toast.makeText(this,it.message.toString(),Toast.LENGTH_SHORT)}



                }
            }
        }
    }

    companion object{
        const val TITLE = "title"
        const val DESCRIPTION = "desc"
        const val ID = "id"
        const val VIDEOS = "videos"
    }

    override val viewModel: ViewModel
        by lazy { ViewModelProvider(this) [ViewModel::class.java] }

    override fun initView() {
        super.initView()
        binding.recyclerView.apply { adapter = this@MainActivity.adapter }
        adapter.notifyDataSetChanged()
    }
}
