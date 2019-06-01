package com.jbseppanen.nikecodechallenge

import java.io.Serializable

data class Album(
    val artistId: String,
    val artistName: String,
    val artistUrl: String,
    val artworkUrl100: String,
    val contentAdvisoryRating: String,
    val copyright: String,
    val genres: List<Genre>,
    val id: String,
    val kind: String,
    val name: String,
    val releaseDate: String,
    val url: String
) : Serializable

data class Genre(
    val genreId: String,
    val name: String,
    val url: String
) : Serializable
