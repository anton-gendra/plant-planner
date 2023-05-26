package com.apm.plant_planner.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.apm.plant_planner.R
import com.squareup.picasso.Picasso
import java.net.URL

class SearchViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    val plantName = itemView.findViewById<TextView>(R.id.textView18)
    val plantimage = itemView.findViewById<ImageView>(R.id.imageView3)

    fun create_items(searchItemModel: SearchItem) {
        plantName.text = searchItemModel.plantName
        Picasso.get().load(searchItemModel.plantImage).placeholder(R.drawable.emojione_1f331).into(plantimage)
    }



}