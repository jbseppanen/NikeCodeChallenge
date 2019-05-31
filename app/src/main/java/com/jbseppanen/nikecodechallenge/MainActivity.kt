package com.jbseppanen.nikecodechallenge

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log

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
