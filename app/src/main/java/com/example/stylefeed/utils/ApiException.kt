package com.example.stylefeed.utils

class ApiException(val code: Int, override val message: String) : Exception(message)