package com.example.foodspoilagetracker

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var foodRecyclerView: RecyclerView
    private lateinit var adapter: FoodItemAdapter
    private lateinit var fabAddFood: FloatingActionButton
    private val sharedPrefFile = "com.example.foodspoilagetracker.PREFERENCE_FILE_KEY"
    private val gson = Gson()
    private var foodList: MutableList<FoodItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        foodRecyclerView = findViewById(R.id.foodRecyclerView)
        fabAddFood = findViewById(R.id.fabAddFood)

        // Load saved data.
        loadData()

        adapter = FoodItemAdapter(foodList)
        foodRecyclerView.layoutManager = LinearLayoutManager(this)
        foodRecyclerView.adapter = adapter

        // Setup swipe-to-delete.
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // Remove the item from adapter and update persistence.
                val position = viewHolder.adapterPosition
                adapter.removeItem(position)
                saveData()
            }
        })
        itemTouchHelper.attachToRecyclerView(foodRecyclerView)

        // Floating Action Button: open dialog to add new food item.
        fabAddFood.setOnClickListener {
            showAddFoodDialog()
        }
    }

    private fun showAddFoodDialog() {
        // Inflate the custom dialog layout.
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_food, null)
        val etFoodName = dialogView.findViewById<EditText>(R.id.etFoodName)
        val btnSelectDate = dialogView.findViewById<Button>(R.id.btnSelectDate)
        val tvSelectedDate = dialogView.findViewById<TextView>(R.id.tvSelectedDate)
        var selectedDateInMillis: Long? = null

        // Set up a date picker.
        btnSelectDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(this, { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                selectedDateInMillis = calendar.timeInMillis
                val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
                tvSelectedDate.text = sdf.format(calendar.time)
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        // Build and show the AlertDialog.
        AlertDialog.Builder(this)
            .setTitle("Add Food Item")
            .setView(dialogView)
            .setPositiveButton("Save") { dialog, _ ->
                val name = etFoodName.text.toString().trim()
                if (name.isNotEmpty() && selectedDateInMillis != null) {
                    val newFoodItem = FoodItem(name = name, expirationDate = selectedDateInMillis!!)
                    adapter.addItem(newFoodItem)
                    saveData()
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
            .create()
            .show()
    }

    private fun saveData() {
        // Convert list to JSON and save to SharedPreferences.
        val sharedPref = getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        val jsonString = gson.toJson(adapter.getItems())
        editor.putString("food_items", jsonString)
        editor.apply()
    }

    private fun loadData() {
        val sharedPref = getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val jsonString = sharedPref.getString("food_items", null)
        if (jsonString != null) {
            try {
                val type = object : TypeToken<MutableList<FoodItem>>() {}.type
                foodList = gson.fromJson(jsonString, type)
            } catch (e: Exception) {
                Log.e("MainActivity", "Error parsing saved food items", e)
            }
        }
    }
}
