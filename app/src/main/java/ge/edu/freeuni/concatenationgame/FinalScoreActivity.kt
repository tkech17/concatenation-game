package ge.edu.freeuni.concatenationgame

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class FinalScoreActivity : AppCompatActivity() {

    private lateinit var resultScoreTextView: TextView
    private lateinit var playAgainButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_final_score)
        playAgainButton = findViewById(R.id.playAgainButton)
        resultScoreTextView = findViewById(R.id.result_score_text_view)

        displayResultMessage()
        playAgainButton.setOnClickListener { startActivity(Intent(this, GameActivity::class.java)) }
    }

    @SuppressLint("SetTextI18n")
    private fun displayResultMessage() {
        val score: Int = intent.getIntExtra("score", 0)
        resultScoreTextView.text = "Your Score Is $score"
    }

}
