package com.example.infopulse.extentions

fun String.formatDate(): String {
    return this.substring(0, 16).replace("T", " ")
}