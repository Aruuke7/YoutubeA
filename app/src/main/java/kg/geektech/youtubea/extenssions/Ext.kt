package kg.geektech.youtubea.extenssions

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.coroutines.withContext

fun ImageView.loadImage(url: String){
    Glide.with(context)
        .load(url)
        .into(this)
}

fun Context.showToast(message: String){
    Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
}