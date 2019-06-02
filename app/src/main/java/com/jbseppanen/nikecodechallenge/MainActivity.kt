package com.jbseppanen.nikecodechallenge

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewAdapter = AlbumListAdapter(this)
        val viewManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        findViewById<RecyclerView>(R.id.recycler_view).apply {
            setHasFixedSize(false)

            layoutManager = viewManager

            adapter = viewAdapter
        }
    }
}
