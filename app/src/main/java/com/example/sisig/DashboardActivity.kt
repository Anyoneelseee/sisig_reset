package com.example.sisig

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat

class DashboardActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var header: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        supportActionBar?.hide()

        header = findViewById(R.id.viewpager_header)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        setupBottomNavigationView(bottomNavigationView)

        viewPager = findViewById(R.id.view_pager)
        setupViewPager(viewPager)

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_home -> {
                    viewPager.currentItem = 0
                    true
                }
                R.id.action_inventory -> {
                    viewPager.currentItem = 1
                    true
                }
                R.id.action_reports -> {
                    viewPager.currentItem = 2
                    true
                }
                R.id.action_notifications -> {
                    viewPager.currentItem = 3
                    true
                }
                R.id.action_settings -> {
                    viewPager.currentItem = 4
                    true
                }
                else -> false
            }
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updateHeaderText(position)
                bottomNavigationView.menu.getItem(position).isChecked = true
            }
        })

        // Set up the menu icon click listener
        val menuIcon: ImageView = findViewById(R.id.menu_icon)
        menuIcon.setOnClickListener { showPopupMenu(menuIcon) }
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.inflate(R.menu.menu_menu)
        popupMenu.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.about -> {
                    // Handle About click
                    true
                }
                R.id.logout -> {
                    confirmLogout()
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

    private fun updateHeaderText(position: Int) {
        val headerText = when (position) {
            0 -> "Home"
            1 -> "Inventory"
            2 -> "Report"
            3 -> "Notifications"
            4 -> "Settings"
            else -> "Home"
        }
        header.text = headerText
    }

    private fun confirmLogout() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirm Logout")
        builder.setMessage("Are you sure you want to logout?")
        builder.setPositiveButton("Yes") { dialog, _ ->
            logout()
            dialog.dismiss()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    private fun logout() {
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            clear()
            apply()
        }

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun setupBottomNavigationView(bottomNavigationView: BottomNavigationView) {
        for (i in 0 until bottomNavigationView.menu.size()) {
            val menuItem = bottomNavigationView.menu.getItem(i)
            val view = LayoutInflater.from(this).inflate(R.layout.nav_item, null)

            val iconResId = when (menuItem.itemId) {
                R.id.action_home -> R.drawable.ic_home
                R.id.action_settings -> R.drawable.ic_settings
                R.id.action_inventory -> R.drawable.ic_inventory
                R.id.action_notifications -> R.drawable.ic_notification
                R.id.action_reports -> R.drawable.ic_report
                else -> R.drawable.ic_home
            }

            view.findViewById<ImageView>(R.id.icon).setImageResource(iconResId)
            view.findViewById<TextView>(R.id.title).text = menuItem.title

            val menuView = bottomNavigationView.getChildAt(0) as ViewGroup
            menuView.getChildAt(i).setBackgroundColor(ContextCompat.getColor(this, R.color.red))
        }
    }

    private fun setupViewPager(viewPager: ViewPager2) {
        val adapter = ViewPagerAdapter(this)
        adapter.addFragment(HomeActivity.newInstance(), "Home")
        adapter.addFragment(InventoryActivity.newInstance(), "Inventory")
        adapter.addFragment(ReportActivity.newInstance(), "Report")
        adapter.addFragment(NotificationsActivity.newInstance(), "Notifications")
        adapter.addFragment(SettingsFragment.newInstance(), "Settings")
        viewPager.adapter = adapter
    }
}