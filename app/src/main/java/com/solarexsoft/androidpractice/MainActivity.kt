package com.solarexsoft.androidpractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.solarexsoft.androidpractice.nestedscrolling.NestedScrollingActivityL
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nestedScrolling.setOnClickListener {
            startActivity(Intent(this, NestedScrollingActivityL::class.java))
        }
    }
}