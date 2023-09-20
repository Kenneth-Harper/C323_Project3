package com.example.c323_project3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.findNavController

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/*
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
        numQuestions = QuestionScreenArgs.fromBundle(requireArguments()).number_questions

        val operationText = view.findViewById<TextView>(R.id.tv_operation)
        when (operation)
        {
            "Addition" -> operationText.text = "+"
            "Multiplication" -> operationText.text = "X"
            "Division" -> operationText.text = "/"
            "Subtraction" -> operationText.text = "-"
        }

        createNewProblem()

        val textInput = view.findViewById<EditText>(R.id.et_answer)

        val doneButton = view.findViewById<Button>(R.id.bt_done)
        doneButton.setOnClickListener {
            if (numberSoFar == numQuestions)
            {
                val action = FinishScreenDirections.action_questionScreen_to_finishScreen(numberCorrect, numQuestions)
                view.findNavController().navigate(action)
            }
            else
            {
                when (operation)
                {
                    "Addition" ->
                    {
                        if (textInput.text.toString() == (var1 + var2).toString())
                        {
                            numberCorrect += 1
                        }
                    }
                    "Multiplication" ->
                    {
                        if (textInput.text.toString() == (var1 * var2).toString())
                        {
                            numberCorrect += 1
                        }
                    }
                    "Division" ->
                    {
                        if (textInput.text.toString() == (var1 / var2).toString())
                        {
                            numberCorrect += 1
                        }
                    }
                    "Subtraction" ->
                    {
                        if (textInput.text.toString() == (var1 - var2).toString())
                        {
                            numberCorrect += 1
                        }
                    }
                }
                numberSoFar++
                createNewProblem()
            }
        }

        return view
    }

    /**
     * <h1> createNewProblem </h1>
     * Function that takes in no parameters, but based on class values randomly generates the values for both variables on either side of the equation.
     *
     */
    private fun createNewProblem()
    {
        when (difficulty)
        {
            "Easy" ->
            {
                var1 = (1 until 10).random()
                var2 = (1 until 10).random()
            }
            "Medium" ->
            {
                var1 = (1 until 25).random()
                var2 = (1 until 25).random()
            }
            "Hard" ->
            {
                var1 = (1 until 50).random()
                var2 = (1 until 50).random()
            }
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment QuestionScreen.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            QuestionScreen().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}