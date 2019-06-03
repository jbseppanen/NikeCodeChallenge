package com.jbseppanen.nikecodechallenge

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL


object NetworkAdapter {
    const val GET = "GET"
    const val POST = "POST"
    const val PUT = "PUT"
    const val DELETE = "DELETE"
    const val TIMEOUT = 3000


    interface NetworkCallback {
        fun returnResult(success: Boolean, result: String)
    }

    fun httpRequest(stringUrl: String, requestType: String, jsonBody: String?, callback: NetworkCallback) {
        Thread(Runnable {
            var result = ""
            var success = false
            var stream: InputStream? = null
            var connection: HttpURLConnection? = null
            try {
                val url = URL(stringUrl)
                connection = url.openConnection() as HttpURLConnection
                connection.readTimeout = TIMEOUT
                connection.connectTimeout = TIMEOUT
                connection.requestMethod = requestType
                connection.setRequestProperty("Content-Type", "application/json")

                if (requestType == GET || requestType == DELETE) {
                    connection.connect()
                } else if (requestType == POST || requestType == PUT) {
                    if (jsonBody != null) {
                        val outputStream = connection.outputStream
                        outputStream.write(jsonBody.toByteArray())
                        outputStream.close()
                    }
                }

                if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                    stream = connection.inputStream
                    if (stream != null) {
                        val reader = BufferedReader(InputStreamReader(stream))
                        val builder = StringBuilder()
                        var line: String? = reader.readLine()
                        while (line != null) {
                            builder.append(line)
                            line = reader.readLine()
                        }
                        result = builder.toString()
                        success = true
                    }
                }

            } catch (e: MalformedURLException) {
                e.printStackTrace()
                result = e.message.toString()
            } catch (e: IOException) {
                e.printStackTrace()
                result = e.message.toString()
            } finally {
                connection?.disconnect()

                if (stream != null) {
                    try {
                        stream.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
            }
            callback.returnResult(success, result)
        }).start()
    }
}