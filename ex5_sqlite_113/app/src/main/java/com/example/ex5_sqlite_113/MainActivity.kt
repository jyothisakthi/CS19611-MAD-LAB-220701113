package com.example.ex5_sqlite_113

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: UsersDBHelper
    private lateinit var editRegisterNumber: EditText
    private lateinit var editName: EditText
    private lateinit var editCGPA: EditText
    private lateinit var textViewRecords: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = UsersDBHelper(this)

        editRegisterNumber = findViewById(R.id.editRegisterNumber)
        editName = findViewById(R.id.editName)
        editCGPA = findViewById(R.id.editCGPA)
        textViewRecords = findViewById(R.id.textViewRecords)

        val buttonInsert: Button = findViewById(R.id.buttonInsert)
        val buttonUpdate: Button = findViewById(R.id.buttonUpdate)
        val buttonDelete: Button = findViewById(R.id.buttonDelete)
        val buttonView: Button = findViewById(R.id.buttonView)
        val buttonClear: Button = findViewById(R.id.buttonClear)  // 🔹 New Clear Button

        buttonInsert.setOnClickListener { insertUser() }
        buttonUpdate.setOnClickListener { updateUser() }
        buttonDelete.setOnClickListener { deleteUser() }
        buttonView.setOnClickListener { viewUsers() }
        buttonClear.setOnClickListener { clearFields() }  // 🔹 Clear fields when clicked
    }

    private fun insertUser() {
        val register = editRegisterNumber.text.toString()
        val name = editName.text.toString()
        val cgpa = editCGPA.text.toString().toDoubleOrNull()

        if (register.isEmpty() || name.isEmpty() || cgpa == null) {
            Toast.makeText(this, "Please enter valid details", Toast.LENGTH_SHORT).show()
            return
        }

        val user = UserModel(register, name, cgpa)
        val success = dbHelper.insertUser(user)

        if (success) {
            Toast.makeText(this, "Inserted Successfully", Toast.LENGTH_SHORT).show()
            clearFields()
        } else {
            Toast.makeText(this, "Insert Failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUser() {
        val register = editRegisterNumber.text.toString()
        val name = editName.text.toString()
        val cgpa = editCGPA.text.toString().toDoubleOrNull()

        if (register.isEmpty() || name.isEmpty() || cgpa == null) {
            Toast.makeText(this, "Please enter valid details", Toast.LENGTH_SHORT).show()
            return
        }

        val user = UserModel(register, name, cgpa)
        val success = dbHelper.updateUser(user)

        if (success) {
            Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT).show()
            clearFields()
        } else {
            Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteUser() {
        val register = editRegisterNumber.text.toString()

        if (register.isEmpty()) {
            Toast.makeText(this, "Please enter Register Number", Toast.LENGTH_SHORT).show()
            return
        }

        val success = dbHelper.deleteUser(register)

        if (success) {
            Toast.makeText(this, "Deleted Successfully", Toast.LENGTH_SHORT).show()
            clearFields()
        } else {
            Toast.makeText(this, "Delete Failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun viewUsers() {
        val users = dbHelper.getAllUsers()
        if (users.isEmpty()) {
            textViewRecords.text = "No records found"
        } else {
            textViewRecords.text = users.joinToString("\n") {
                "RegNo: ${it.registerNumber}, Name: ${it.name}, CGPA: ${it.cgpa}"
            }
        }
    }

    private fun clearFields() {
        editRegisterNumber.text.clear()
        editName.text.clear()
        editCGPA.text.clear()
    }
}
