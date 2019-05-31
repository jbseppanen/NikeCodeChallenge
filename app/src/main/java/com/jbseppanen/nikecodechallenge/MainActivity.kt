package com.jbseppanen.nikecodechallenge

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DataDao.getAlbums(object: DataDao.DataCallback {
            override fun callback(albums: ArrayList<Album>) {
            }
        })
    }
}
