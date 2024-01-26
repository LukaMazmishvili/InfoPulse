package com.example.infopulse.extensions

fun String.formatDate(): String {
    return this.substring(0, 16).replace("T", " ")
}