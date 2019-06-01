package com.jbseppanen.nikecodechallenge

import android.graphics.drawable.Drawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_details.*
import android.content.Intent
import android.net.Uri


class DetailsActivity : AppCompatActivity() {

    companion object {
        const val ALBUM_DETAILS_KEY = "Album_Details"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val album = intent.getSerializableExtra(ALBUM_DETAILS_KEY) as Album

        text_details_album_name.text = album.name
        text_details_artist.text = album.artistName
        var genres = ""
        album.genres.forEach { genre ->
            genres += (genre.name + ",")
        }
        genres.removeSuffix(",")
        text_details_genre.text = genres
        text_details_release.text = album.releaseDate
        text_details_copyright.text = album.copyright

        Glide
            .with(this)
            .load(album.artworkUrl100)
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
            .into(image_details)

        button_details.setOnClickListener {
            val uri = Uri.parse(album.url)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }
}
