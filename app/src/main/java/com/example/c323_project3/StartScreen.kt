package com.example.c323_project3

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.navigation.findNavController

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/*
 * <h1> StartScreen </h1>
 * Fragment class representing the starting screen in the app.
 */
class StartScreen : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var difficulty = "easy"
    private var operation = "+"
    private var numLevels = 10


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_start_screen, container, false)

        //Code for getting radio group for the difficulty
        val rgDifficulty = view.findViewById<RadioGroup>(R.id.rg_difficulty)

        val rgOperation = view.findViewById<RadioGroup>(R.id.rg_operation)

        // Code for selecting the number of questions the user will complete
        val numQText = view.findViewById<TextView>(R.id.tv_numQ)
        val lowerNumQ = view.findViewById<Button>(R.id.bt_lessQ)
        lowerNumQ.setOnClickListener {
            numLevels -= 1
            if (numLevels <= 0)
            {
                numLevels = 1
            }
            numQText.text = numLevels.toString()
        }
        val higherNumQ = view.findViewById<Button>(R.id.bt_moreQ)
        higherNumQ.setOnClickListener {
            numLevels += 1
            numQText.text = numLevels.toString()
        }

        val startButton = view.findViewById<Button>(R.id.bt_start)
        startButton.setOnClickListener {
            val selectedDiff = rgDifficulty!!.checkedRadioButtonId
            val diffButton = view.findViewById<RadioButton>(selectedDiff)
            difficulty = diffButton.text.toString()
            val selectedOp = rgOperation!!.checkedRadioButtonId
            val opButton = view.findViewById<RadioButton>(selectedOp)
            operation = opButton.text.toString()
            Log.v("StartScreen", "Difficulty= $difficulty")
            Log.v("StartScreen", "Operation= $operation")

            val action = StartScreenDirections.actionStartScreenToQuestionScreen(numLevels)
            action.difficulty = difficulty
            action.operation = operation
            view.findNavController().navigate(action)
        }
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment StartScreen.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StartScreen().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}