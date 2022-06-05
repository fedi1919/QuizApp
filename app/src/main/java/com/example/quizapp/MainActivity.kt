package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        val btn_start = findViewById<Button>(R.id.btn_start)

        btn_start.setOnClickListener {
            val edit_text = findViewById<AppCompatEditText>(R.id.et_name)
            if (edit_text.text.toString().isEmpty()){
                Toast.makeText(this,
                    "You can't start unless you enter your name", Toast.LENGTH_SHORT).show()
            }else{
                val intent = Intent(this, QuizQuestionsActivity::class.java) // to move from an activity to another
                intent.putExtra(Constants.USER_NAME, edit_text.text.toString())
                startActivity(intent)
                finish() // to close the current activity
            }
        }

    }
}