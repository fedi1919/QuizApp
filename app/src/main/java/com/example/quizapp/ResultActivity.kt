package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val username = intent.getStringExtra(Constants.USER_NAME)
        val name = findViewById<TextView>(R.id.tv_name)

        name.text = username

        val totalQuestions = intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)
        val correctAnswers = intent.getIntExtra(Constants.CORRECT_ANSWERS,0)
        val score = findViewById<TextView>(R.id.tv_score)

        score.text = "Your score is $correctAnswers out of $totalQuestions"

        val btn = findViewById<Button>(R.id.btn_finish)

        btn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }
}