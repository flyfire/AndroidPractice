package com.solarexsoft.androidpractice

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.solarexsoft.androidpractice.nestedscrolling.NestedScrollingActivityL
import com.solarexsoft.androidpractice.textviewtag.TextViewTagActivity
import com.tencent.mmkv.MMKV
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nestedScrolling.setOnClickListener {
            startActivity(Intent(this, NestedScrollingActivityL::class.java))
        }
        textViewTag.setOnClickListener {
            startActivity(Intent(this, TextViewTagActivity::class.java))
        }
        MMKV.initialize(this)
        val mmkv = MMKV.mmkvWithID("solarex")
        val test = mmkv.decodeParcelable("123", Test::class.java)
        Log.d("solarex", "test = $test")
    }
}

@Parcelize
data class Test(
    val name: String
) : Parcelable