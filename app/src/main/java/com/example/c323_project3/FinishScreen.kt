package com.example.c323_project3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/*
 * <h1> FinishScreen </h1>
 * Fragment class representing the final screen in the app.
 * @param numCorrect :
 *      the number of correct answers given in the previous screen
 * @param numQuestions :
 *      the number of questions selected to be given in the previous screen
 */
class FinishScreen : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_finish_screen, container, false)

        val numCorrect = FinishScreenArgs.fromBundle(requireArguments()).numberCorrect
        val numQuestions = FinishScreenArgs.fromBundle(requireArguments()).numberQuestions

        val resultText = view.findViewById<TextView>(R.id.tv_results)
        resultText.text = "$numCorrect out of $numQuestions"

        val doneButton = view.findViewById<Button>(R.id.bt_toStart)
        doneButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_finishScreen_to_startScreen)
        }
        return view
    }
}