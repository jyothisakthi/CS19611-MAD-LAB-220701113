package com.example.exp7_sdcard_113

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private val STORAGE_PERMISSION_CODE = 101  // Request code for permissions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val registerNumberEditText = findViewById<EditText>(R.id.registerNumberEditText)
        val nameEditText = findViewById<EditText>(R.id.nameEditText)
        val cgpaEditText = findViewById<EditText>(R.id.cgpaEditText)
        val saveButton = findViewById<Button>(R.id.saveButton)

        // Check and request permissions if needed
        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE)

        saveButton.setOnClickListener {
            val registerNumber = registerNumberEditText.text.toString()
            val name = nameEditText.text.toString()
            val cgpa = cgpaEditText.text.toString()

            if (registerNumber.isEmpty() || name.isEmpty() || cgpa.isEmpty()) {
                Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show()
            } else {
                saveToExternalStorage(registerNumber, name, cgpa)
            }
        }
    }

    // Check permission function
    private fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
        }
    }

    // Handle permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Storage Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Storage Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Function to save data to SD Card
    private fun saveToExternalStorage(registerNumber: String, name: String, cgpa: String) {
        if (isExternalStorageWritable()) {
            val folder = File(getExternalFilesDir(null), "StudentData")
            if (!folder.exists()) folder.mkdirs()

            val file = File(folder, "StudentDetails.txt")
            try {
                FileOutputStream(file, true).bufferedWriter().use { writer ->
                    writer.appendLine("Register Number: $registerNumber, Name: $name, CGPA: $cgpa")
                }
                Toast.makeText(this, "Data saved successfully!", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                Toast.makeText(this, "Error saving data: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "External storage not available", Toast.LENGTH_SHORT).show()
        }
    }

    // Check if external storage is writable
    private fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }
}
