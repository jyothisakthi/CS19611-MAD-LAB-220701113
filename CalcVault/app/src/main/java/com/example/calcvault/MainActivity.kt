package com.example.calcvault
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var inputText: EditText
    private lateinit var outputText: TextView

    private val storedPin = "1234"  // Static PIN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputText = findViewById(R.id.inputText)
        outputText = findViewById(R.id.outputText)

        // Button listeners
        findViewById<Button>(R.id.b0).setOnClickListener { appendToInput("0") }
        findViewById<Button>(R.id.b1).setOnClickListener { appendToInput("1") }
        findViewById<Button>(R.id.b2).setOnClickListener { appendToInput("2") }
        findViewById<Button>(R.id.b3).setOnClickListener { appendToInput("3") }
        findViewById<Button>(R.id.b4).setOnClickListener { appendToInput("4") }
        findViewById<Button>(R.id.b5).setOnClickListener { appendToInput("5") }
        findViewById<Button>(R.id.b6).setOnClickListener { appendToInput("6") }
        findViewById<Button>(R.id.b7).setOnClickListener { appendToInput("7") }
        findViewById<Button>(R.id.b8).setOnClickListener { appendToInput("8") }
        findViewById<Button>(R.id.b9).setOnClickListener { appendToInput("9") }
        findViewById<Button>(R.id.bplus).setOnClickListener { appendToInput("+") }
        findViewById<Button>(R.id.bminus).setOnClickListener { appendToInput("-") }
        findViewById<Button>(R.id.bmul).setOnClickListener { appendToInput("*") }
        findViewById<Button>(R.id.bdiv).setOnClickListener { appendToInput("/") }
        findViewById<Button>(R.id.bdot).setOnClickListener { appendToInput(".") }

        findViewById<Button>(R.id.bclr).setOnClickListener { clearInput() }
        findViewById<Button>(R.id.beq).setOnClickListener { evaluateExpression() }
    }

    private fun appendToInput(value: String) {
        val currentInput = inputText.text.toString() + value
        inputText.setText(currentInput)

        if (currentInput == "$storedPin++") {
            openVault()
        }
    }

    private fun clearInput() {
        inputText.text.clear()
        outputText.text = ""
    }

    private fun evaluateExpression() {
        val expression = inputText.text.toString()

        // If user typed the vault trigger, do not evaluate
        if (expression == "$storedPin++") return

        try {
            val result = calculate(expression)
            outputText.text = result.toString()
        } catch (e: Exception) {
            outputText.text = "Error"
        }
    }

    // Manual evaluator for simple expressions
    private fun calculate(expr: String): Double {
        var expression = expr.replace(" ", "")
        var result = 0.0
        var current = ""
        var operator = '+'

        for (i in expression.indices) {
            val c = expression[i]
            if (c.isDigit() || c == '.') {
                current += c
            }

            if (!c.isDigit() && c != '.' || i == expression.lastIndex) {
                val num = current.toDouble()
                when (operator) {
                    '+' -> result += num
                    '-' -> result -= num
                    '*' -> result *= num
                    '/' -> result /= num
                }
                operator = c
                current = ""
            }
        }

        return result
    }

    private fun openVault() {
        Toast.makeText(this, "Vault Opened", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, VaultActivity::class.java)
        startActivity(intent)
    }
}
