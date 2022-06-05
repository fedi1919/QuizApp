package com.example.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentPosition: Int = 1
    private var mQuestionsList: ArrayList<Question>? = null
    private var mSelectedOptionPosition:  Int = 0
    private var mCorrectAnswers: Int = 0
    private var mUserName: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        mUserName = intent.getStringExtra(Constants.USER_NAME)

        mQuestionsList = Constants.getQuestions()
        //Log.i("Questions size" , "${questionsList.size}")

        val optOne = findViewById<TextView>(R.id.tv_option_one)
        val optTwo = findViewById<TextView>(R.id.tv_option_two)
        val optThree = findViewById<TextView>(R.id.tv_option_three)
        val optFour = findViewById<TextView>(R.id.tv_option_four)
        val buttonSubmit =findViewById<Button>(R.id.btn_submit)

        setQuestion()

        optOne.setOnClickListener(this)
        optTwo.setOnClickListener(this)
        optThree.setOnClickListener(this)
        optFour.setOnClickListener(this)
        buttonSubmit.setOnClickListener(this)
    }

    private  fun setQuestion(){


        val question = mQuestionsList!![mCurrentPosition-1]

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val progressText = findViewById<TextView>(R.id.tv_progress)
        val tvQuestion = findViewById<TextView>(R.id.tv_question)
        val image = findViewById<ImageView>(R.id.iv_image)
        val optOne = findViewById<TextView>(R.id.tv_option_one)
        val optTwo = findViewById<TextView>(R.id.tv_option_two)
        val optThree = findViewById<TextView>(R.id.tv_option_three)
        val optFour = findViewById<TextView>(R.id.tv_option_four)
        val buttonSubmit =findViewById<Button>(R.id.btn_submit)

        defaultOptionView() // reset all the options to deafult in case of a new question

        if(mCurrentPosition == mQuestionsList!!.size){
            buttonSubmit.text = "FINISH"
        }else{
            buttonSubmit.text = "SUBMIT"
        }

        progressBar.progress = mCurrentPosition
        progressText.text = "$mCurrentPosition/${progressBar.max}"
        tvQuestion.text = question.question
        image.setImageResource(question.image)
        optOne.text = question.optionOne
        optTwo.text = question.optionTwo
        optThree.text = question.optionThree
        optFour.text = question.optionFour
    }

    private fun defaultOptionView(){
        val options = ArrayList<TextView>()

        val optOne = findViewById<TextView>(R.id.tv_option_one)
        val optTwo = findViewById<TextView>(R.id.tv_option_two)
        val optThree = findViewById<TextView>(R.id.tv_option_three)
        val optFour = findViewById<TextView>(R.id.tv_option_four)

        options.add(0, optOne)
        options.add(1, optTwo)
        options.add(2, optThree)
        options.add(3, optFour)

        for (option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }
    }

    override fun onClick(v: View?) {

        val optOne = findViewById<TextView>(R.id.tv_option_one)
        val optTwo = findViewById<TextView>(R.id.tv_option_two)
        val optThree = findViewById<TextView>(R.id.tv_option_three)
        val optFour = findViewById<TextView>(R.id.tv_option_four)
        val buttonSubmit =findViewById<Button>(R.id.btn_submit)

        when(v?.id){
            R.id.tv_option_one ->{
                selectedOptionView(optOne,1)
            }
            R.id.tv_option_two ->{
                selectedOptionView(optTwo,2)
            }
            R.id.tv_option_three -> {
                selectedOptionView(optThree,3)
            }
            R.id.tv_option_four -> {
                selectedOptionView(optFour,4)
            }
            R.id.btn_submit -> {
                if (mSelectedOptionPosition == 0){
                    mCurrentPosition++

                    when{
                        mCurrentPosition <= mQuestionsList!!.size -> {
                            setQuestion()
                        }else -> {
                            val intent = Intent(this, ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME, mUserName)
                            intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
                            intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList!!.size)
                            startActivity(intent)
                            finish()
                        }
                    }
                }else{ // if the user choose an answer
                    val question = mQuestionsList?.get(mCurrentPosition-1)
                    if(question!!.correctAnswer != mSelectedOptionPosition){
                        answerView(mSelectedOptionPosition, R.drawable.incorrect_option_border_bg)
                    }else{
                        mCorrectAnswers++
                    }
                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                    if(mCurrentPosition == mQuestionsList!!.size){
                        buttonSubmit.text = "FINISH"
                    }else{
                        buttonSubmit.text = "GO TO THE NEXT QUESTION"
                    }
                    mSelectedOptionPosition = 0 // to go to the next question
                }

            }
        }
    }

    private fun answerView(answer: Int, drawableView: Int){

        val optOne = findViewById<TextView>(R.id.tv_option_one)
        val optTwo = findViewById<TextView>(R.id.tv_option_two)
        val optThree = findViewById<TextView>(R.id.tv_option_three)
        val optFour = findViewById<TextView>(R.id.tv_option_four)

        when(answer){
            1 -> {
                optOne.background = ContextCompat.getDrawable(this, drawableView)
            }
            2 -> {
                optTwo.background = ContextCompat.getDrawable(this, drawableView)
            }
            3 -> {
                optThree.background = ContextCompat.getDrawable(this, drawableView)
            }
            4 -> {
                optFour.background = ContextCompat.getDrawable(this, drawableView)
            }
        }

    }

    private fun selectedOptionView(tv : TextView, selectedOptionNum: Int){
        defaultOptionView()
        mSelectedOptionPosition = selectedOptionNum

        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)
    }
}