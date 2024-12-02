import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.example.sisig.R

class NewStockDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_new_stock, container, false)

        val productNameInput: EditText = view.findViewById(R.id.product_name_input)
        val itemsInput: EditText = view.findViewById(R.id.items_input)
        val categoryInput: EditText = view.findViewById(R.id.category_input)
        val confirmButton: Button = view.findViewById(R.id.confirm_new_stock)
        val cancelButton: Button = view.findViewById(R.id.cancel_new_stock)

        confirmButton.setOnClickListener {
            val productName = productNameInput.text.toString()
            val items = itemsInput.text.toString()
            val category = categoryInput.text.toString()

            if (productName.isNotEmpty() && items.isNotEmpty() && category.isNotEmpty()) {
                val result = Bundle().apply {
                    putString("productName", productName)
                    putString("items", items)
                    putString("category", category)
                }
                setFragmentResult("stockResult", result)
                dismiss()
            } else {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        cancelButton.setOnClickListener { dismiss() }
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)

        return view
    }
}







