package com.solarexsoft.androidpractice

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.solarexsoft.androidpractice.nestedscrolling.NestedScrollingActivityL
import com.solarexsoft.androidpractice.textviewtag.TextViewTagActivity
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
    }
}