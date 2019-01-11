package com.bignerdranch.android.geoquize
import android.nfc.Tag
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_quiz.*
import java.util.function.ToDoubleFunction

class QuizActivity : AppCompatActivity() {
    val TAG ="QuizActivity"
    val KEY_INDEX = "index"
    var NumberOfTrue = 0
    private var CurrentIndex = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        if (savedInstanceState != null){
            CurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0)
        }

        RefreshQuestion()

        next_button.setOnClickListener{
            CurrentPlus()
            RefreshQuestion()
        }

        true_button.setOnClickListener {
            CheckAnswer(true)
        }

        false_button.setOnClickListener {
            CheckAnswer(false)
        }

        question_text?.setOnClickListener {
            CurrentPlus()
            RefreshQuestion()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: called")
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle?) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState: ")
        savedInstanceState?.putInt(KEY_INDEX, CurrentIndex)
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: called")
    }

    val QuestionBank = arrayOf(
            Question(R.string.question_australia, true),
            Question(R.string.question_oceans, true),
            Question(R.string.question_mideast, false),
            Question(R.string.question_africa, false),
            Question(R.string.question_americas, true),
            Question(R.string.question_asia, true))


    fun RefreshQuestion(){
        question_text?.text = getText(QuestionBank[CurrentIndex].TextResId)
    }

    private fun CheckAnswer (UserPressedTrue: Boolean){
        var answerIsTrue = QuestionBank[CurrentIndex].AnswerTrue
        var MessageResId: Int
        if (UserPressedTrue==answerIsTrue){
            MessageResId = R.string.correct_answer
            NumberOfTrue++
            Log.d(TAG, "CheckAnswer:" + NumberOfTrue)
        }
        else{
            MessageResId = R.string.incorrect_answer
        }
        Toast.makeText(this, MessageResId, Toast.LENGTH_SHORT).show()
        CurrentPlus()
        RefreshQuestion()
    }

    fun CurrentPlus(){
        CurrentIndex++
        if (CurrentIndex == QuestionBank.size){
            var PercentOfTrue = (NumberOfTrue*100/QuestionBank.size)
            val toast = Toast.makeText(this,"$PercentOfTrue% correct answers.", Toast.LENGTH_LONG)
            toast.show()
            NumberOfTrue = 0
        }
        CurrentIndex = CurrentIndex%QuestionBank.size
    }
}
