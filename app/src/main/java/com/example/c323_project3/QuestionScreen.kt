package com.example.c323_project3

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import java.math.BigDecimal
import java.math.RoundingMode

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * <h1> QuestionScreen </h1>
 * Fragment class representing the question screen in the app.
 * @param difficulty : string
 *      The string dictating how difficult the questions are
 * @param operation : string
 *      The string dictating what type of operations will be done
 * @param number_questions : int
 *      The int telling the screen how many times to ask a question
 */
class QuestionScreen : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    /*
     * Variables responsible for the smooth operation of the question fragment
     */
    private var difficulty = "Easy"
    private var operation = "Addition"
    private var numQuestions = 10
    private var var1 = 0
    private var var2 = 0
    private var numberSoFar = 1
    private var numberCorrect = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_question_screen, container, false)

        difficulty = QuestionScreenArgs.fromBundle(requireArguments()).difficulty
        operation = QuestionScreenArgs.fromBundle(requireArguments()).operation
        numQuestions = QuestionScreenArgs.fromBundle(requireArguments()).numberQuestions

        val operationText = view.findViewById<TextView>(R.id.tv_operation)
        when (operation)
        {
            "Addition" -> operationText.text = "+"
            "Multiplication" -> operationText.text = "X"
            "Division" -> operationText.text = "/"
            "Subtraction" -> operationText.text = "-"
        }

        val var1Text = view.findViewById<TextView>(R.id.tv_var1)
        val var2Text = view.findViewById<TextView>(R.id.tv_var2)
        createNewProblem(var1Text, var2Text)

        val textInput = view.findViewById<EditText>(R.id.et_answer)

        val doneButton = view.findViewById<Button>(R.id.bt_done)
        doneButton.setOnClickListener {
            if (numberSoFar == numQuestions)
            {
                checkCorrectness(textInput.text.toString())
                val action = QuestionScreenDirections.actionQuestionScreenToStartScreen()
                action.numberCorrect = numberCorrect
                view.findNavController().navigate(action)
            }
            else
            {
                checkCorrectness(textInput.text.toString())
                numberSoFar++
                createNewProblem(var1Text, var2Text)
                textInput.text.clear()
            }
        }

        return view
    }

    /**
     * <h1> checkCorrectness </h1>
     * Function used to determine if an input answer is correct for the given equation
     * @param inputNumber
     *      The input number as a string that will be evaluated against
     */
    private fun checkCorrectness(inputNumber : String)
    {
        var correct = false
        when (operation) {
            "Addition" -> {
                if (inputNumber == (var1 + var2).toString()) {
                    numberCorrect += 1
                    correct = true
                }
            }

            "Multiplication" -> {
                if (inputNumber == (var1 * var2).toString()) {
                    numberCorrect += 1
                    correct = true
                }
            }

            "Division" -> {
                val calculatedResult = (var1.toFloat() / var2.toFloat()).toString()
                val roundedResult = BigDecimal(calculatedResult).setScale(2, RoundingMode.HALF_EVEN)
                Log.v("Question-Division: ","RoundedResult = $roundedResult")
                val roundedInput = BigDecimal(inputNumber).setScale(2, RoundingMode.HALF_EVEN)
                Log.v("Question-Division: ","RoundedInput = $roundedInput")
                if (roundedInput == roundedResult) {
                    numberCorrect += 1
                    correct = true
                }
            }

            "Subtraction" -> {
                if (inputNumber == (var1 - var2).toString()) {
                    numberCorrect += 1
                    correct = true
                }
            }
        }

        if (correct)
        {
            val text = "Correct. Good work!"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(this.context, text, duration)
            toast.show()

            var mediaPlayer = MediaPlayer.create(this.context, R.raw.correct)
            mediaPlayer.start()

            mediaPlayer.release()
            mediaPlayer = null
        }
        else
        {
            val text = "Wrong"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(this.context, text, duration)
            toast.show()

            var mediaPlayer = MediaPlayer.create(this.context, R.raw.wrong)
            mediaPlayer.start()

            mediaPlayer.release()
            mediaPlayer = null
        }
    }

    /**
     * <h1> createNewProblem </h1>
     * Function that takes in no parameters, but based on class values randomly generates the values for both variables on either side of the equation.
     * @param var1Text
     *      TextView object that displays the first variable
     * @param var2Text
     *      Textview object that displays the second variable
     */
    private fun createNewProblem(var1Text : TextView, var2Text : TextView)
    {
        when (difficulty)
        {
            "Easy" -> {
                var1 = (1 until 10).random()
                var2 = (1 until 10).random()
            }

            "Medium" -> {
                var1 = (1 until 25).random()
                var2 = (1 until 25).random()
            }

            "Hard" -> {
                var1 = (1 until 50).random()
                var2 = (1 until 50).random()
            }
        }

        var1Text.text = var1.toString()
        var2Text.text = var2.toString()
    }
}