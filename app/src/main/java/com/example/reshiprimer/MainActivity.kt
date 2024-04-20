package com.example.reshiprimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.reshiprimer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var numOneTextView: TextView
    private lateinit var operationTextView: TextView
    private lateinit var numTwoTextView: TextView
    private lateinit var numThreeEditText: EditText
    private lateinit var startButton: Button
    private lateinit var checkButton: Button
    private lateinit var winTextView: TextView
    private lateinit var fallTextView: TextView
    private lateinit var winCodeTextView: TextView
    private lateinit var fallCodeTextView: TextView
    private lateinit var vsoNumTextView: TextView

    private var correctAnswers: Int = 0
    private var incorrectAnswers: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // binding.numOne.text = "4674"
        // Initialize views
        numOneTextView = binding.numOne
        operationTextView = binding.operation
        numTwoTextView = binding.numTwo
        numThreeEditText = binding.numThreeRes
        startButton = binding.butStart
        checkButton = binding.butProverka
        winTextView = binding.win
        fallTextView = binding.fall
        winCodeTextView = binding.winCode
        fallCodeTextView = binding.fallCode
        vsoNumTextView = binding.vsoNum

        // Set onClickListeners
        startButton.setOnClickListener {
            generateNumbers()
            startButton.isEnabled = false
            checkButton.isEnabled = true
            numThreeEditText.isEnabled = true
        }

        checkButton.setOnClickListener {
            val answer = numThreeEditText.text.toString().toInt()
            val numOne = numOneTextView.text.toString().toInt()
            val numTwo = numTwoTextView.text.toString().toInt()
            val operation = operationTextView.text.toString()
            val result = calculateResult(numOne, numTwo, operation)
            if (answer == result) {
                correctAnswers++
                numThreeEditText.setBackgroundColor(resources.getColor(android.R.color.holo_green_light))
            } else {
                incorrectAnswers++
                numThreeEditText.setBackgroundColor(resources.getColor(android.R.color.holo_red_light))
            }
            updateStatistics()
            numThreeEditText.isEnabled = false
            checkButton.isEnabled = false
        }

        // Start the loop
        mainLoop()
    }

    private fun mainLoop() {
        while (true) {
            generateNumbers()
            startButton.isEnabled = false
            checkButton.isEnabled = true
            numThreeEditText.isEnabled = true
            // Wait until the user checks the answer
            while (!checkButton.isEnabled) {
                Thread.sleep(100) // Приостановка выполнения потока на 100 миллисекунд
            }
        }
    }

    private fun generateNumbers() {
        val randomNumOne = (10..99).random()
        val randomNumTwo = (10..99).random()
        numOneTextView.text = randomNumOne.toString()
        numTwoTextView.text = randomNumTwo.toString()
        operationTextView.text = generateOperation()
        numThreeEditText.setText("")
        numThreeEditText.setBackgroundColor(resources.getColor(android.R.color.white))
    }

    private fun generateOperation(): String {
        val operations = arrayOf("+", "-", "*", "/")
        return operations.random()
    }

    private fun calculateResult(numOne: Int, numTwo: Int, operation: String): Int {
        return when (operation) {
            "+" -> numOne + numTwo
            "-" -> numOne - numTwo
            "*" -> numOne * numTwo
            "/" -> numOne / numTwo
            else -> throw IllegalArgumentException("Invalid operation")
        }
    }

    private fun updateStatistics() {
        val totalAttempts = correctAnswers + incorrectAnswers
        val accuracy = if (totalAttempts > 0) {
            (correctAnswers.toDouble() / totalAttempts.toDouble()) * 100
        } else {
            0.0
        }
        winTextView.text = correctAnswers.toString()
        fallTextView.text = incorrectAnswers.toString()
        winCodeTextView.text = String.format("%.2f", accuracy) + "%"
        fallCodeTextView.text = (totalAttempts - correctAnswers).toString()
        vsoNumTextView.text = totalAttempts.toString()
    }
}