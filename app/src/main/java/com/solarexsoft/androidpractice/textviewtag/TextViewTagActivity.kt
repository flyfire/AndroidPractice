package com.solarexsoft.androidpractice.textviewtag

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import androidx.appcompat.app.AppCompatActivity
import com.solarexsoft.androidpractice.R
import kotlinx.android.synthetic.main.activity_textviewtag.*

/**
 * Created by houruhou on 2021/7/28/3:53 PM
 * Desc:
 */
class TextViewTagActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_textviewtag)
        val spannableString = SpannableString("      哈利·波特邀请Cho参加舞会哈利·波特邀请Cho参加舞会哈利·波特邀请Cho参加舞会哈利·波特邀请Cho参加舞会哈利·波特邀请Cho参加舞会哈利·波特邀请Cho参加舞会")
        val span = TextViewTagSpan(this, R.drawable.vd_tag, 2)
        spannableString.setSpan(span,0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv.text = spannableString
    }
}