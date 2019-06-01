package com.jbseppanen.nikecodechallenge

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

class AlbumListAdapter(val activity: Activity) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class AlbumItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val albumImage: ImageView = view.findViewById(R.id.image_album_art)
        val albumNameView: TextView = view.findViewById(R.id.text_album_name)
        val artistNameView: TextView = view.findViewById(R.id.text_artist_name)
    }

    val data = mutableListOf<Album>()


    init {
        getItems()
    }

    private fun getItems() {
        DataDao.getAlbums(object : DataDao.DataCallback {
            override fun callback(albums: ArrayList<Album>) {
                data.addAll(albums)
                activity.runOnUiThread { notifyDataSetChanged() }
            }
        })
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AlbumItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.album_item_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, index: Int) {
        val element = data[index]
        val albumHolder = viewHolder as AlbumItemViewHolder

        albumHolder.albumNameView.text = element.name
        albumHolder.artistNameView.text = element.artistName
        albumHolder.albumImage.setOnClickListener {
            val albumIntent = Intent(activity, DetailsActivity::class.java)
            albumIntent.putExtra(DetailsActivity.ALBUM_DETAILS_KEY, element)
            activity.startActivity(albumIntent)
        }

        Glide
            .with(activity)
            .load(element.artworkUrl100)
            .apply(RequestOptions().centerCrop())
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            })
            .into(albumHolder.albumImage)
    }
}