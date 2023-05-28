package com.example.a401v3

import android.content.Context
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Toast
import android.media.MediaPlayer
import java.util.*

class EquationsGameActivity : AppCompatActivity() {

    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var intentRecognizer: Intent
    private lateinit var correctSound: MediaPlayer
    private lateinit var wrongSound: MediaPlayer
    private val random = Random()
    private var currentAnswer = 0
    private var score = 0
    private var highScore = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_equations_game)

        // Load the high score from shared preferences
        highScore = getSharedPreferences("AlphaMath", Context.MODE_PRIVATE)
            .getInt("equations_high_score", 0)

        // Set the high score TextView
        findViewById<TextView>(R.id.highScoreTextView).text = "High Score: $highScore"

        // Create MediaPlayer instances for correct and wrong sound effects
        correctSound = MediaPlayer.create(this, R.raw.correct)
        wrongSound = MediaPlayer.create(this, R.raw.wrong)

        // Create SpeechRecognizer and RecognizerIntent
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        intentRecognizer = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
        }

        // Set RecognitionListener for speech recognition
        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle) {}

            override fun onBeginningOfSpeech() {}

            override fun onRmsChanged(rmsdB: Float) {}

            override fun onBufferReceived(buffer: ByteArray) {}

            override fun onEndOfSpeech() {}

            override fun onError(error: Int) {}

            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (matches != null && matches.isNotEmpty()) {
                    val spokenText = matches[0].toIntOrNull()
                    if (spokenText == currentAnswer) {
                        // If the spoken number matches the current answer, play correct sound and increment score
                        correctSound.start()
                        incrementScore()
                    } else {
                        // If the spoken number does not match the current answer, play wrong sound, show toast, and end game
                        wrongSound.start()
                        Toast.makeText(this@EquationsGameActivity, "Wrong answer. Try again!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }

            override fun onPartialResults(partialResults: Bundle) {}

            override fun onEvent(eventType: Int, params: Bundle) {}
        })

        // Set click listener for the microphone button to start speech recognition
        findViewById<Button>(R.id.micButton).setOnClickListener {
            speechRecognizer.startListening(intentRecognizer)
        }

        // Generate the first equation
        generateNewEquation()
    }

    // Function to generate a new equation
    private fun generateNewEquation() {
        val firstNumber = random.nextInt(90) + 10 // Generate a two-digit number
        val secondNumber = random.nextInt(90) + 10 // Generate a two-digit number
        if (random.nextBoolean()) {
            // Addition
            currentAnswer = firstNumber + secondNumber
            findViewById<TextView>(R.id.equationTextView).text = "$firstNumber + $secondNumber"
        } else {
            // Subtraction with positive result
            if (firstNumber >= secondNumber) {
                currentAnswer = firstNumber - secondNumber
                findViewById<TextView>(R.id.equationTextView).text = "$firstNumber - $secondNumber"
            } else {
                currentAnswer = secondNumber - firstNumber
                findViewById<TextView>(R.id.equationTextView).text = "$secondNumber - $firstNumber"
            }
        }
    }

    // Function to increment the score and update the high score if necessary
    private fun incrementScore() {
        score++
        findViewById<TextView>(R.id.scoreTextView).text = "Score: $score"
        if (score > highScore) {
            highScore = score
            // Save the high score to shared preferences
            getSharedPreferences("AlphaMath", Context.MODE_PRIVATE).edit()
                .putInt("equations_high_score", highScore)
                .apply()
            findViewById<TextView>(R.id.highScoreTextView).text = "High Score: $highScore"
        }
        // Generate a new equation
        generateNewEquation()
    }
}
