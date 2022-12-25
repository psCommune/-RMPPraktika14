package com.example.praktika14

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val handler = android.os.Handler()
        android.os.Handler().postDelayed({
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)}, 2000)
    }
}