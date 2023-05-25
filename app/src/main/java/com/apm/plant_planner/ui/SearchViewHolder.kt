package com.apm.plant_planner.ui

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.apm.plant_planner.R

class SearchViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    val plantName = itemView.findViewById<TextView>(R.id.textView18)

    fun create_items(searchItemModel: SearchItem) {
        plantName.text = searchItemModel.plantName
    }
}