package com.solarexsoft.androidpractice.textviewtag

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.style.DynamicDrawableSpan
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

/**
 * Created by houruhou on 2021/7/28/3:20 PM
 * Desc:
 */
class TextViewTagSpan(
    private val context: Context,
    @DrawableRes private val drawableRes: Int,
    align: Int
) : DynamicDrawableSpan(align) {
    override fun getDrawable(): Drawable {
        val drawable = ContextCompat.getDrawable(context, drawableRes)
        drawable!!.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        return drawable
    }
}