package com.example.stylefeed

object FileUtil {
    fun readTestResource(path: String): String {
        val inputStream = this.javaClass.classLoader!!.getResourceAsStream(path)
        return inputStream.bufferedReader().use { it.readText() }
    }
}