package com.jbseppanen.nikecodechallenge

import org.json.JSONException
import org.json.JSONObject

object DataDao {

    private const val API_URL = "https://rss.itunes.apple.com/api/v1/us/apple-music/coming-soon/all/100/explicit.json"

    interface DataCallback {
        fun callback(albums: ArrayList<Album>)
    }

    fun getAlbums(callback: DataCallback) {
        NetworkAdapter.httpRequest(API_URL, NetworkAdapter.GET, "", object : NetworkAdapter.NetworkCallback {
            override fun returnResult(success: Boolean?, result: String) {
                if (success == true) {
                    val albums = ArrayList<Album>()
                    //Parse Json
                    val topLevel = JSONObject(result)
                    val nextLevel = topLevel.getJSONObject("feed")
                    val jsonArray = nextLevel.getJSONArray("results")
                    for (i in 0 until jsonArray.length()) {
                        try { //Album would be skipped if error parsing Json.  Perhaps better to run a try/catch on each as I did with contentAdvisoryRating.
                            val json: JSONObject = jsonArray[i] as JSONObject
                            val genreArray = json.getJSONArray("genres")
                            val genreList = ArrayList<Genre>()
                            for (j in 0 until genreArray.length()) {
                                val gJson: JSONObject = genreArray[j] as JSONObject
                                val genre = Genre(
                                    genreId = gJson.getString("genreId"),
                                    name = gJson.getString("name"),
                                    url = gJson.getString("url")
                                )
                                genreList.add(genre)
                            }
                            val advisoryRating = try {
                                json.getString("contentAdvisoryRating")
                            } catch (e: JSONException) {
                                "Unknown"
                            }
                            val album = Album(
                                artistId = json.getString("artistId"),
                                artistName = json.getString("artistName"),
                                artistUrl = json.getString("artistUrl"),
                                artworkUrl100 = json.getString("artworkUrl100"),
                                contentAdvisoryRating = advisoryRating,
                                copyright = json.getString("copyright"),
                                genres = genreList,
                                id = json.getString("id"),
                                kind = json.getString("kind"),
                                name = json.getString("name"),
                                releaseDate = json.getString("releaseDate"),
                                url = json.getString("url")
                            )
                            albums.add(album)
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                    callback.callback(albums)
                }
            }
        })
    }
}