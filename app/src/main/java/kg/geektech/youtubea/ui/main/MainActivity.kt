package kg.geektech.youtubea.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kg.geektech.youtubea.`object`.Constant
import kg.geektech.youtubea.ui.detail.PlaylistDetailActivity
import kg.geektech.youtubea.base.BaseActivity
import kg.geektech.youtubea.common.Network
import kg.geektech.youtubea.common.Status
import kg.geektech.youtubea.databinding.ActivityMainBinding
import kg.geektech.youtubea.models.Items
import kg.geektech.youtubea.ui.ViewModel

class MainActivity : BaseActivity<ViewModel, ActivityMainBinding>() {
    private var adapter = PlaylistAdapter(this::initClick)


    override fun inflateViewBinding(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(inflater)
    }

    private fun initClick(id: Items,title:String,desc:String,count:String) {
        Intent(this, PlaylistDetailActivity::class.java).apply {
            putExtra(Constant.KEY_PLAYLIST_ID, id.id)
            putExtra(Constant.TITLE,title)
            putExtra(Constant.DESC,desc)
            putExtra(Constant.COUNT,count)
            startActivity(this)
        }
    }

    override fun initListener() {
        super.initListener()
        binding.noInternetCheck.btnConnect.setOnClickListener {
            checkInternet()
        }
    }

    override fun checkInternet() {
        super.checkInternet()
        val connection = Network(application)
        connection.observe(this) { isConnected ->
            if (isConnected) {
                binding.apply {
                    recyclerView.visibility = VISIBLE
                    noInternetCheck.parentConnection.visibility = INVISIBLE
                }
            } else {
                binding.apply {
                    recyclerView.visibility = INVISIBLE
                    noInternetCheck.parentConnection.visibility = VISIBLE
                }
            }
        }
    }

    override fun initViewModel() {
        super.initViewModel()

        viewModel.getPlaylist().observe(this) {
            if (it != null) {
                when (it.status) {
                    Status.LOADING -> {
                        binding.progressBar.visibility = VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.progressBar.visibility = INVISIBLE
                        binding.recyclerView.visibility = VISIBLE
                        it.data?.let { it1 -> adapter.setList(it1.items) }
                    }
                    Status.ERROR -> {
                        binding.progressBar.visibility = INVISIBLE
                        binding.noInternetCheck.parentConnection.visibility = VISIBLE
                        Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override val viewModel: ViewModel
            by lazy { ViewModelProvider(this)[ViewModel::class.java] }

    @SuppressLint("NotifyDataSetChanged")
    override fun initView() {
        super.initView()
        val manager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = manager
        binding.recyclerView.adapter = adapter

    }
}
