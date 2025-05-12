package com.example.calcvault

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SetPinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("vault", Context.MODE_PRIVATE)
        if (prefs.getString("pin", null) != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        setContentView(R.layout.activity_set_pin)

        val pinEditText = findViewById<EditText>(R.id.pin_edittext)
        val saveButton = findViewById<Button>(R.id.save_button)

        saveButton.setOnClickListener {
            val pin = pinEditText.text.toString()
            if (pin.length < 4) {
                Toast.makeText(this, "PIN must be at least 4 digits", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            prefs.edit().putString("pin", pin).apply()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
