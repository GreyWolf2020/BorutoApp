package com.example.borutoapp.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult

internal object PaletteGenerator {
    suspend fun convertImageUrlToBitmap(
       imageurl: String,
       context: Context
    ): Bitmap? {
        val loader = ImageLoader(context)
        val requester = ImageRequest
            .Builder(context)
            .data(imageurl)
            .allowHardware(false)
            .build()
        val imageResult = loader.execute(requester)
        return if (imageResult is SuccessResult) {
            (imageResult.drawable as BitmapDrawable).bitmap
        } else {
            null
        }
    }

    fun extractColorFromBitMap(bitmap: Bitmap): Map<String, String> {
        return mapOf<String, String>(
            "vibrant" to parseColorSwatch(
                Palette.from(bitmap).generate().vibrantSwatch
            ),
            "darkVibrant" to parseColorSwatch(
                Palette.from(bitmap).generate().darkVibrantSwatch
            ),
            "onDarkVibrant" to parseBodyColor(Palette.from(bitmap).generate().darkVibrantSwatch?.bodyTextColor)

        )
    }

    private fun parseColorSwatch(color: Palette.Swatch?): String {
        return if (color != null) {
            val parsedColor = Integer.toHexString(color.rgb)
            "#$parsedColor"
        } else {
            "#000000"
        }
    }

    private fun parseBodyColor(color: Int?): String {
        Log.d("DetailsViewModel", "$color")
        return if (color != null) {
            val parseColor = Integer.toHexString(color)
            "#$parseColor"
        } else {
            "#FFFFFF"
        }
    }
}
