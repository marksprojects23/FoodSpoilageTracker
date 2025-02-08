package com.example.foodspoilagetracker

import java.util.*

data class FoodItem(
    val id: String = UUID.randomUUID().toString(),
    var name: String,
    var expirationDate: Long
) {
    // Returns true if the current date is past the expiration date.
    fun isExpired(): Boolean = System.currentTimeMillis() > expirationDate
}
