package com.example.sisig

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class MenuAdapter(
    private val menuItems: List<MenuItem>,
    private val onAddClick: (MenuItem) -> Unit // Lambda for handling clicks
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    // ViewHolder to hold views for each menu item
    class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val serving: TextView = itemView.findViewById(R.id.serving)
        val description: TextView = itemView.findViewById(R.id.description)
        val price: TextView = itemView.findViewById(R.id.price)
        val imageView: ImageView = itemView.findViewById(R.id.image)
        val addButton: Button = itemView.findViewById(R.id.add_button) // "+" button
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        // Inflate the layout for menu items
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.menu_item_layout, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        // Bind the menu item data to the views
        val menuItem = menuItems[position]
        holder.name.text = menuItem.name
        holder.serving.text = menuItem.serving
        holder.description.text = menuItem.description
        holder.price.text = "₱${menuItem.price}"

        // Set a placeholder image or an actual image resource if available
        holder.imageView.setImageResource(menuItem.imageResId);
        // Set a click listener for the "+" button
        holder.addButton.setOnClickListener {
            // Inflate the place order dialog layout
            val dialogView = LayoutInflater.from(holder.itemView.context).inflate(R.layout.place_order_layout, null)

            // Build and display the dialog
            val dialogBuilder = AlertDialog.Builder(holder.itemView.context)
                .setView(dialogView)
                .setCancelable(false) // Make dialog non-cancelable if desired
                .create()

            // Find views in the dialog
            val nameTextView = dialogView.findViewById<TextView>(R.id.menu_name)
            val descriptionTextView = dialogView.findViewById<TextView>(R.id.menu_description)
            val servingTextView = dialogView.findViewById<TextView>(R.id.menu_serving)
            val priceTextView = dialogView.findViewById<TextView>(R.id.menu_price)
            val menuImageView = dialogView.findViewById<ImageView>(R.id.menu_image) // Reference to image
            val quantityTextView = dialogView.findViewById<TextView>(R.id.quantity)
            val totalCostTextView = dialogView.findViewById<TextView>(R.id.total_cost)

            // Set the menu item details
            nameTextView.text = menuItem.name
            descriptionTextView.text = menuItem.description
            servingTextView.text = menuItem.serving
            priceTextView.text = "₱${menuItem.price}"

            // Load the image (You can use a real image if available)
            menuImageView.setImageResource(menuItem.imageResId)
            // Replace with actual resource
            // Initial cost (same as price at the start)
            var quantity = 1
            var totalCost = menuItem.price
            totalCostTextView.text = "₱$totalCost"

            // Increase and Decrease Quantity Listeners
            dialogView.findViewById<Button>(R.id.increase_quantity).setOnClickListener {
                quantity++
                quantityTextView.text = quantity.toString()
                totalCost = menuItem.price * quantity
                totalCostTextView.text = "₱$totalCost"
            }

            dialogView.findViewById<Button>(R.id.decrease_quantity).setOnClickListener {
                if (quantity > 1) {
                    quantity--
                    quantityTextView.text = quantity.toString()
                    totalCost = menuItem.price * quantity
                    totalCostTextView.text = "₱$totalCost"
                }
            }

            // Set button actions
            dialogView.findViewById<Button>(R.id.confirm_button).setOnClickListener {
                onAddClick(menuItem)
                // Handle confirm action (e.g., add the order with quantity and total cost)
                dialogBuilder.dismiss()
            }

            dialogView.findViewById<Button>(R.id.cancel_button).setOnClickListener {
                dialogBuilder.dismiss()
            }

            // Show the dialog
            dialogBuilder.show()
        }
    }

    override fun getItemCount(): Int {
        // Return the total number of menu items
        return menuItems.size
    }

    fun getItems(): List<MenuItem> {
        return menuItems
    }
}
