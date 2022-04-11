package kg.geektech.youtubea.ui.detail

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kg.geektech.youtubea.`object`.Constant
import kg.geektech.youtubea.base.BaseActivity
import kg.geektech.youtubea.common.Network
import kg.geektech.youtubea.common.Status
import kg.geektech.youtubea.databinding.ActivityPlaylistDetailBinding
import kg.geektech.youtubea.models.Items
import kg.geektech.youtubea.ui.ViewModel

class PlaylistDetailActivity : BaseActivity<ViewModel, ActivityPlaylistDetailBinding>() {
    private var adapter = DetailAdapter(this::initClick)
    private lateinit var  id:String

    private fun initClick(id: Items) {

    }

    override val viewModel: ViewModel
            by lazy { ViewModelProvider(this)[ViewModel::class.java] }


    override fun inflateViewBinding(inflater: LayoutInflater): ActivityPlaylistDetailBinding {
        return ActivityPlaylistDetailBinding.inflate(inflater)
    }

    override fun initView() {
        super.initView()
        val manager = LinearLayoutManager(this)
        binding.playlistRv.layoutManager = manager
        binding.playlistRv.adapter = adapter

    }

    override fun initViewModel() {
        super.initViewModel()
        id = intent.getStringExtra(Constant.KEY_PLAYLIST_ID)!!
        Log.d("ololo", "initViewModel: $id")
        viewModel.getPlaylistById(id).observe(this) {
            if (it != null) {
                when (it.status) {
                    Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.progressBar.visibility = View.INVISIBLE
                        binding.playlistRv.visibility = View.VISIBLE
                       binding.appBar.visibility = View.VISIBLE
                        it.data?.let { it1 -> adapter.setList(it1.items) }
                    }
                    Status.ERROR -> {
                        binding.progressBar.visibility = View.INVISIBLE
                        binding.checkInet.parentConnection.visibility = View.VISIBLE
                        Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }


    override fun initListener() {
        super.initListener()
        binding.checkInet.btnConnect.setOnClickListener {
            checkInternet()
        }
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun checkInternet() {
        super.checkInternet()
        val connection = Network(application)
        connection.observe(this) { isConnected ->
            if (isConnected) {
                binding.apply {
                    playlistRv.visibility = View.VISIBLE
                    appBar.visibility = View.VISIBLE
                    checkInet.parentConnection.visibility = View.INVISIBLE
                    titleTv.text = intent.getStringExtra(Constant.TITLE)
                    descTv.text = intent.getStringExtra(Constant.DESC)
                    binding.seriesTv.text = intent.getStringExtra(Constant.COUNT)


                }
            } else {
                binding.apply {
                    playlistRv.visibility = View.INVISIBLE
                    appBar.visibility = View.INVISIBLE
                    checkInet.parentConnection.visibility = View.VISIBLE
                }
            }
        }
    }
}