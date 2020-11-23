package com.example.app_kotlin.data.model

import android.graphics.Color
import android.os.Parcelable
import com.example.app_kotlin.R
import com.example.app_kotlin.data.noteId
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Note(
    val id: Long = noteId,
    val title: String = "",
    val note: String = "",
    val color: Int = randomColor()
) : Parcelable

fun randomColor(): Int {
    val colorList = listOf(
        colorOne, colorSecond, colorThird, colorFour, colorW,
        colorFifth, colorSix, colorSeven, colorOnef, colorSecondf,
        colorThirdf, colorFourf, colorFifthf, colorSixf, colorSevenf,
    )

    fun List<Int>.random() = if (size > 0) get(Random().nextInt(size)) else 0
    return colorList.random()

}

val colorOne = Color.rgb(202, 217, 214)
val colorSecond = Color.rgb(68, 12, 131)
val colorThird = Color.rgb(92, 108, 121)
val colorFour = Color.rgb(191, 209, 211)
val colorFifth = Color.rgb(167, 207, 209)
val colorSix = Color.rgb(168, 180, 186)
val colorSeven = Color.rgb(122, 190, 195)
val colorW = Color.rgb(225, 225, 225)
val colorOnef = Color.rgb(162, 87, 189)
val colorSecondf = Color.rgb(136, 227, 172)
val colorThirdf = Color.rgb(237, 186, 250)
val colorFourf = Color.rgb(198, 134, 214)
val colorFifthf = Color.rgb(96, 187, 225)
val colorSixf = Color.rgb(221, 110, 151)
val colorSevenf = Color.rgb(241, 224, 149)

