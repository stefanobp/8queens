package com.StefanoBaldini.aplicacion1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.StefanoBaldini.myapplication.R

class PrimeraAppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_primera_app)

        val buttonGoToInstructions = findViewById<Button>(R.id.buttonGoToInstructions)
        buttonGoToInstructions.setOnClickListener {
            val intent = Intent(this, InstructionsActivity::class.java)
            startActivity(intent)
        }

        val buttonGoToGame = findViewById<Button>(R.id.buttonGoToGame)
        buttonGoToGame.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }
    }
}
