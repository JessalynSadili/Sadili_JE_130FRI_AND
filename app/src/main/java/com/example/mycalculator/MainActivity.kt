package com.example.mycalculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private var operator: String? = null
    private var firstValue: Double? = null
    private var secondValue: Double? = null
    private var isNewOp = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.display)

        // Button Clicks
        val buttons = intArrayOf(
            R.id.button0, R.id.button1, R.id.button2,
            R.id.button3, R.id.button4, R.id.button5,
            R.id.button6, R.id.button7, R.id.button8,
            R.id.button9, R.id.buttonAdd, R.id.buttonSubtract,
            R.id.buttonMultiply, R.id.buttonDivide, R.id.buttonClear, R.id.buttonEquals
        )

        for (buttonId in buttons) {
            findViewById<Button>(buttonId).setOnClickListener { onButtonClick(it) }
        }
    }

    private fun onButtonClick(view: View) {
        val button = view as Button
        when (button.id) {
            R.id.buttonClear -> clear()
            R.id.buttonEquals -> calculate()
            else -> {
                if (button.text.toString() in listOf("+", "-", "*", "/")) {
                    setOperator(button.text.toString())
                } else {
                    appendToDisplay(button.text.toString())
                }
            }
        }
    }

    private fun appendToDisplay(value: String) {
        if (isNewOp) {
            display.text = value
            isNewOp = false
        } else {
            display.append(value)
        }
    }

    private fun clear() {
        display.text = "0"
        operator = null
        firstValue = null
        secondValue = null
        isNewOp = true
    }

    private fun setOperator(op: String) {
        if (operator != null) {
            calculate()
        }
        operator = op
        firstValue = display.text.toString().toDoubleOrNull()

        // Append the operator to the display
        appendToDisplay(" $op ")
        isNewOp = true
    }

    private fun calculate() {
        if (operator == null || isNewOp) return
        secondValue = display.text.toString().trim().split(" ").last().toDoubleOrNull()

        val result = when (operator) {
            "+" -> (firstValue ?: 0.0) + (secondValue ?: 0.0)
            "-" -> (firstValue ?: 0.0) - (secondValue ?: 0.0)
            "*" -> (firstValue ?: 0.0) * (secondValue ?: 0.0)
            "/" -> if ((secondValue ?: 0.0) != 0.0) (firstValue ?: 0.0) / (secondValue ?: 0.0) else Double.NaN
            else -> Double.NaN
        }

        // Format the result to two decimal places
        val formattedResult = if (result.isNaN()) {
            "Error"
        } else {
            String.format("%.2f", result)
        }

        display.text = formattedResult
        operator = null
        isNewOp = true
    }
}



