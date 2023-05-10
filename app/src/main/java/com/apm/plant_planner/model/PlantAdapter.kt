package com.apm.plant_planner.model

import android.content.Context
import android.graphics.BitmapFactory
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.apm.plant_planner.R
import java.io.File

class PlantAdapter(
    private val context: Context,
    private val plants: List<Plant>
) : BaseAdapter() {
    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return plants.size
    }

    override fun getItem(position: Int): Any {
        return plants[position]
    }

    // for simplicity, we will use the position as the id
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.list_item_plant, parent, false)
        val plantImageView = rowView.findViewById(R.id.plant_image) as ImageView
        val nameTextView = rowView.findViewById(R.id.plant_name) as TextView
        val typeTextView = rowView.findViewById(R.id.plant_type) as TextView

        val plant = getItem(position) as Plant
        nameTextView.text = plant.plant_name
        typeTextView.text = plant.plant_type

        if (plant.bitmapFileName != null) {
            val file = File(context.filesDir, plant.bitmapFileName)
            val bitmap = BitmapFactory.decodeFile(file.absolutePath)
            plantImageView.setImageBitmap(bitmap)
        }

        return rowView
    }


}