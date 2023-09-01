package com.bukinmg.testapplication.utils

import com.bukinmg.testapplication.R

fun String?.toModel(): String{
    return when(this) {
        "Sercomm G450" -> "Vera Plus"
        "Sercomm G550" -> "Vera Secure"
        "MiCasaVerde VeraLite" -> "Vera Edge"
        "Sercomm NA900" -> "Vera Edge"
        "Sercomm NA301" -> "Vera Edge"
        "Sercomm NA930" -> "Vera Edge"
        else -> "Vera Edge"
    }
}

fun String.toImageResId(): Int {
    return when(this) {
        "Vera Plus" -> R.drawable.vera_plus_big
        "Vera Secure" -> R.drawable.vera_secure_big
        "Vera Edge" -> R.drawable.vera_edge_big
        else -> R.drawable.vera_edge_big
    }
}