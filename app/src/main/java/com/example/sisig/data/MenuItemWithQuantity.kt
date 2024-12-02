package com.example.sisig.data

import com.example.sisig.MenuItem

data class MenuItemWithQuantity(
    val menuItem: List<MenuItem>,
    var quantity: Int = 1
)