package com.bignerdranch.android.geoquize
import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.res.TypedArrayUtils.getText
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_quiz.*

@Suppress("UNREACHABLE_CODE")
class QuizActivity : AppCompatActivity() {
    val TAG ="isAnswerShow?"
    val KEY_INDEX = "index"
    var NumberOfTrue = 0
    private var CurrentIndex = 0
    private var isCheater = false
    var cheatIsLeft = 3
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        if (savedInstanceState != null){
            CurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0)
            isCheater = savedInstanceState.getBoolean("cheater?", true)
        }

        RefreshQuestion()

        next_button.setOnClickListener{
            CurrentPlus()
            RefreshQuestion()
        }

        true_button.setOnClickListener {
            CheckAnswer(true)
        }

        false_button?.setOnClickListener {
            CheckAnswer(false)
        }

        question_text?.setOnClickListener {
            CurrentPlus()
            RefreshQuestion()
        }

        cheat_button.setOnClickListener {
            var answerIsTrue = QuestionBank[CurrentIndex].AnswerTrue
            val intent: Intent = Intent(this, CheatActivity::class.java)
            //Передаем правильный ответ в CheatActivity
            intent.putExtra("TrueAnswer", answerIsTrue)
            intent.putExtra("cheatIsLeft", cheatIsLeft)
            startActivityForResult(intent, 0)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null){
            if (data.getIntExtra("cheatIsClick", 0)>0){
                isCheater = true
            }
            cheatIsLeft = data.getIntExtra("cheatIsLeft", 0)
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle?) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState?.putInt(KEY_INDEX, CurrentIndex)
        savedInstanceState?.putBoolean("cheater?", isCheater)
        savedInstanceState?.putInt("cheatIsLeft", cheatIsLeft)
    }

    var QuestionBank = arrayOf(
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
        if (isCheater){
            MessageResId = R.string.judgment_toast
        }
        else {
            if (UserPressedTrue == answerIsTrue) {
                MessageResId = R.string.correct_answer
                NumberOfTrue++
            } else {
                MessageResId = R.string.incorrect_answer
            }
        }
        Toast.makeText(this, MessageResId, Toast.LENGTH_SHORT).show()
        CurrentPlus()
        RefreshQuestion()
        isCheater = false
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
