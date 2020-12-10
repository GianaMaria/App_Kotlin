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
    val color: Color = Random.nextEnum(),
    val color2: Int = 0,
) : Parcelable

inline fun <reified T : Enum<T>> Random.nextEnum(): T {
    return enumValues<T>().random(this)
}

enum class Color {
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

fun Color.mapToColor(context: Context): Int {
    val id = when (this) {
        Color.COLOR_WHITE -> R.color.white
        Color.COLOR_ONE -> R.color.color_one
        Color.COLOR_SECOND -> R.color.color_second
        Color.COLOR_THIRD -> R.color.color_third
        Color.COLOR_FOUR -> R.color.color_four
        Color.COLOR_FIFTH -> R.color.color_fifth
        Color.COLOR_SIX -> R.color.color_six
        Color.COLOR_SEVEN -> R.color.color_seven
        Color.COLOR_ONE_F -> R.color.white
        Color.COLOR_SECOND_F -> R.color.color_secondf
        Color.COLOR_THIRD_F -> R.color.color_thirdf
        Color.COLOR_FOUR_F -> R.color.color_fourf
        Color.COLOR_FIFTH_F -> R.color.color_fifthf
        Color.COLOR_SIX_F -> R.color.color_sixf
        Color.COLOR_SEVEN_F -> R.color.color_sevenf
    }
    return ContextCompat.getColor(context, id)
}


