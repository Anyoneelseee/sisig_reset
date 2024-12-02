package com.example.sisig

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotificationsAdapter(
    private val notifications: MutableList<String>,
    private val updateNotificationVisibilityCallback: () -> Unit // Add callback parameter
) : RecyclerView.Adapter<NotificationsAdapter.NotificationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notif_item, parent, false)
        return NotificationViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(notifications[position], position)
    }

    override fun getItemCount(): Int {
        return notifications.size
    }

    inner class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val notificationText: TextView = itemView.findViewById(R.id.notification_text)
        private val removeButton: Button = itemView.findViewById(R.id.remove_notification)

        fun bind(notification: String, position: Int) {
            notificationText.text = notification

            // Remove notification when button is clicked
            removeButton.setOnClickListener {
                notifications.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, notifications.size)

                // Call the callback function to update notification visibility
                updateNotificationVisibilityCallback()
            }
        }
    }
}


