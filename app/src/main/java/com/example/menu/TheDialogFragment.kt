package com.example.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class TheDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_the_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val titleTextView = view.findViewById<TextView>(R.id.dialog_title)
        val messageTextView = view.findViewById<TextView>(R.id.dialog_message)
        val positiveButton = view.findViewById<Button>(R.id.positive_button)
        val negativeButton = view.findViewById<Button>(R.id.negative_button)

        titleTextView.text = "For Dialog"
        messageTextView.text = "Choose either positive or negative."

        positiveButton.setOnClickListener {
            val anotherFragment = HomeFragment()
            requireFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, anotherFragment)
                .addToBackStack(null)
                .commit()
            dismiss()
        }

        negativeButton.setOnClickListener {
            dismiss()
        }
    }
}