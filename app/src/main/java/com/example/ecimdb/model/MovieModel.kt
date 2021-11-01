package com.example.ecimdb.model

import java.util.*

data class MovieModel(
    var id: Int = getAutoID(),
    var name: String = "",
    var note: String = ""
) {
    companion object {
        fun getAutoID(): Int {
            val random = Random()
            return random.nextInt(100)
        }
    }
}