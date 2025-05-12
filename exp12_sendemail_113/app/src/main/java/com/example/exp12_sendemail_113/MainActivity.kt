package com.example.exp12_sendemail_113

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var recipientEmail: EditText
    private lateinit var emailSubject: EditText
    private lateinit var emailMessage: EditText
    private lateinit var sendEmailButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recipientEmail = findViewById(R.id.recipientEmail)
        emailSubject = findViewById(R.id.emailSubject)
        emailMessage = findViewById(R.id.emailMessage)
        sendEmailButton = findViewById(R.id.sendEmailButton)

        sendEmailButton.setOnClickListener {
            sendEmail()
        }
    }

    private fun sendEmail() {
        val recipient = recipientEmail.text.toString().trim()
        val subject = emailSubject.text.toString().trim()
        val message = emailMessage.text.toString().trim()

        if (recipient.isNotEmpty() && subject.isNotEmpty() && message.isNotEmpty()) {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:") // only email apps should handle this
                putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, message)
            }

            // Check if there's an email client
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(this, "No email client found", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
        }
    }
}
