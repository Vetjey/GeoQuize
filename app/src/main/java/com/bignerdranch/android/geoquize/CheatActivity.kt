package com.bignerdranch.android.geoquize

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_cheat.*
import kotlinx.android.synthetic.main.activity_quiz.*

class CheatActivity : AppCompatActivity() {
    val TAG:String = "isAnswerShow?"
    var cheatIsLeft = 3
    var cheatIsClick = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)
        // Получаем правильный ответ из главной активности
        val answerIsTrue:Boolean = intent.getBooleanExtra("TrueAnswer", true)
        cheatIsLeft = intent.getIntExtra("cheatIsLeft", 0)
        if(savedInstanceState!=null){
            cheatIsLeft = savedInstanceState.getInt("cheatIsLeft", 0)
        }
        if (cheatIsLeft == 0){
            show_answer_button.isEnabled = false
        }
        RefreshCheatLeft()
        show_answer_button.setOnClickListener{
            if (answerIsTrue==true){
                true_answer_text.setText(R.string.true_button)
            }
            else {
                true_answer_text.setText(R.string.false_button)
            }
            cheatIsLeft--
            cheatIsClick++
            RefreshCheatLeft()
            show_answer_button.isEnabled = false
            setAnswerShowResult(true)
        }
    }

    fun setAnswerShowResult (isAnswerShow:Boolean){
        val data = Intent(this, QuizActivity::class.java)
        data.putExtra("cheatIsClick", cheatIsClick)
        data.putExtra("cheatIsLeft", cheatIsLeft)
        setResult(Activity.RESULT_OK, data)
    }


    override fun onSaveInstanceState(savedInstanceState: Bundle?) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState?.putInt("cheatIsLeft", cheatIsLeft)
    }

    fun RefreshCheatLeft () {
        api_version_textView.text = "$cheatIsLeft cheat left"
    }
}
