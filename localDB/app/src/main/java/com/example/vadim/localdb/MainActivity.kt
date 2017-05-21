package com.example.vadim.localdb

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.textView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        linearLayout {
            textView("32")
        }
    }
}
