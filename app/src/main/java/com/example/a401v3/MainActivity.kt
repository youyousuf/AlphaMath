// Sayed Ali Altabtabai    S00056865
// Ahmed AlSharhan         S00053024
// Yousuf Landolsi         S00035712

package com.example.a401v3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Declare Buttons as member variables so they can be accessed anywhere within the class
    private lateinit var wordGameButton: Button
    private lateinit var numbersGameButton: Button
    private lateinit var equationsGameButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the layout for this Activity
        setContentView(R.layout.activity_main)

        // Initialize the buttons using the findViewById method
        wordGameButton = findViewById(R.id.wordGameButton)
        numbersGameButton = findViewById(R.id.numbersGameButton)
        equationsGameButton = findViewById(R.id.equationsGameButton)

        // Set a click listener for the alphabet game button. When clicked, it opens the AlphabetGameActivity
        wordGameButton.setOnClickListener {
            val intent = Intent(this, WordGameActivity::class.java)
            startActivity(intent)
        }

        // Set a click listener for the numbers game button. When clicked, it opens the NumbersGameActivity
        numbersGameButton.setOnClickListener {
            val intent = Intent(this, NumbersGameActivity::class.java)
            startActivity(intent)
        }

        // Set a click listener for the equations game button. When clicked, it opens the EquationsGameActivity
        equationsGameButton.setOnClickListener {
            val intent = Intent(this, EquationsGameActivity::class.java)
            startActivity(intent)
        }
    }
}
