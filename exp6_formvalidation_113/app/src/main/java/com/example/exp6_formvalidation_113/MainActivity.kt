package com.example.exp6_formvalidation_113

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var editUsername: EditText
    private lateinit var editUserID: EditText
    private lateinit var buttonLogin: Button
    private lateinit var buttonClear: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editUsername = findViewById(R.id.editUsername)
        editUserID = findViewById(R.id.editUserID)
        buttonLogin = findViewById(R.id.buttonLogin)
        buttonClear = findViewById(R.id.buttonClear)

        buttonLogin.setOnClickListener {
            validateInput()
        }

        buttonClear.setOnClickListener {
            clearFields()
        }
    }

    private fun validateInput() {
        val username = editUsername.text.toString().trim()
        val userID = editUserID.text.toString().trim()

        if (username.isEmpty() || userID.isEmpty()) {
            showToast("Fields cannot be empty")
            return
        }

        if (!username.matches(Regex("^[a-zA-Z]+$"))) {
            showToast("Username must contain only alphabets")
            return
        }

        if (!userID.matches(Regex("^\\d{4}$"))) {
            showToast("User ID must be a 4-digit number")
            return
        }

        val intent = Intent(this, MainActivity2::class.java)
        intent.putExtra("USERNAME", username)
        startActivity(intent)
    }

    private fun clearFields() {
        editUsername.text.clear()
        editUserID.text.clear()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
