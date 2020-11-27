package com.example.app_kotlin.data.model

import android.content.Context
import android.os.Parcelable
import androidx.core.content.ContextCompat
import com.example.app_kotlin.R
import com.example.app_kotlin.data.noteId
import kotlinx.android.parcel.Parcelize
import kotlin.random.Random

@Parcelize
data class Note(
    val id: Long = noteId,
    val title: String = "",
    val note: String = "",
    val color: ColorF = Random.nextEnum()
) : Parcelable

inline fun <reified T : Enum<T>> Random.nextEnum(): T {
    return enumValues<T>().random(this)
}

enum class ColorF {
    COLOR_ONE,
    COLOR_SECOND,
    COLOR_THIRD,
    COLOR_FOUR,
    COLOR_FIFTH,
    COLOR_SIX,
    COLOR_SEVEN,
    COLOR_ONE_F,
    COLOR_SECOND_F,
    COLOR_THIRD_F,
    COLOR_FOUR_F,
    COLOR_FIFTH_F,
    COLOR_SIX_F,
    COLOR_SEVEN_F,
    COLOR_WHITE,
}

fun ColorF.mapToColor(context: Context): Int {
    val id = when (this) {
        ColorF.COLOR_WHITE -> R.color.white
        ColorF.COLOR_ONE -> R.color.color_one
        ColorF.COLOR_SECOND -> R.color.color_second
        ColorF.COLOR_THIRD -> R.color.color_third
        ColorF.COLOR_FOUR -> R.color.color_four
        ColorF.COLOR_FIFTH -> R.color.color_fifth
        ColorF.COLOR_SIX -> R.color.color_six
        ColorF.COLOR_SEVEN -> R.color.color_seven
        ColorF.COLOR_ONE_F -> R.color.white
        ColorF.COLOR_SECOND_F -> R.color.color_secondf
        ColorF.COLOR_THIRD_F -> R.color.color_thirdf
        ColorF.COLOR_FOUR_F -> R.color.color_fourf
        ColorF.COLOR_FIFTH_F -> R.color.color_fifthf
        ColorF.COLOR_SIX_F -> R.color.color_sixf
        ColorF.COLOR_SEVEN_F -> R.color.color_sevenf
    }
    return ContextCompat.getColor(context, id)
}


