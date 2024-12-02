package com.example.sisig

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult

class SetAlertDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_set_alert, container, false)

        // Get references to the views
        val productNameInput: EditText = view.findViewById(R.id.product_name_alert_input)
        val alertAmtInput: EditText = view.findViewById(R.id.alert_amt_input)
        val itemsInput: EditText = view.findViewById(R.id.items_alert_input)

        val confirmButton: Button = view.findViewById(R.id.confirm_set_alert)
        val cancelButton: Button = view.findViewById(R.id.cancel_set_alert)

        // Handle confirm button click
        confirmButton.setOnClickListener {
            val productName = productNameInput.text.toString()
            val alertAmt = alertAmtInput.text.toString()
            val items = itemsInput.text.toString()

            if (productName.isNotEmpty() && alertAmt.isNotEmpty() && items.isNotEmpty()) {
                // Pass the data back to the parent fragment (InventoryActivity)
                val result = Bundle().apply {
                    putString("productName", productName)
                    putString("alertAmt", alertAmt)
                    putString("items", items)
                }
                setFragmentResult("alertResult", result) // Send data back to parent fragment
                dismiss() // Close the dialog
            } else {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        // Handle cancel button click
        cancelButton.setOnClickListener {
            dismiss() // Close the dialog
        }

        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        return view
    }
}


