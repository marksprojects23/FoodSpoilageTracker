package com.example.foodspoilagetracker

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class FoodItemAdapter(
    private var items: MutableList<FoodItem>
) : RecyclerView.Adapter<FoodItemAdapter.FoodViewHolder>() {

    private val dateFormatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())

    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvFoodName: TextView = itemView.findViewById(R.id.tvFoodName)
        val tvExpirationDate: TextView = itemView.findViewById(R.id.tvExpirationDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_food, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val item = items[position]
        holder.tvFoodName.text = item.name
        holder.tvExpirationDate.text = dateFormatter.format(Date(item.expirationDate))
        // Change text color to red if the item is expired.
        if (item.isExpired()) {
            holder.tvFoodName.setTextColor(Color.RED)
            holder.tvExpirationDate.setTextColor(Color.RED)
        } else {
            holder.tvFoodName.setTextColor(Color.BLACK)
            holder.tvExpirationDate.setTextColor(Color.BLACK)
        }
    }

    override fun getItemCount(): Int = items.size

    fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun addItem(item: FoodItem) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun updateItems(newItems: MutableList<FoodItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    fun getItems(): MutableList<FoodItem> = items
}
