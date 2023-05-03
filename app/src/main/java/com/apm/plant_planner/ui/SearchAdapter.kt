package com.apm.plant_planner.ui;

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.apm.plant_planner.R
import com.google.android.material.internal.ContextUtils.getActivity
import java.util.Locale
import com.google.gson.Gson

class SearchAdapter (private val originalList: List<SearchItems>) : RecyclerView.Adapter<SearchAdapter.MyViewHolder>(),
    Filterable {

        private var plantList : List<String> = emptyList()

        private var filteredList: List<SearchItems> = originalList.toList()

        inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val textView1: TextView = itemView.findViewById(R.id.textView18)
            val textView2: TextView = itemView.findViewById(R.id.textView19)
            val textView3: TextView = itemView.findViewById(R.id.textView20)
            val textView4: TextView = itemView.findViewById(R.id.textView21)
        }

        fun updatePlantList(plantList: List<String>) {
            this.plantList = plantList
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.search_item, parent, false)
            return MyViewHolder(itemView)
            // Aquí puedes inflar el diseño de cada elemento de la lista y devolver una instancia de MyViewHolder
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val item = filteredList[position]
            holder.textView1.text = item.plantName1
            holder.textView2.text = item.plantName2
            holder.textView3.text = item.plantName3
            holder.textView4.text = item.plantName4
            // Aquí puedes actualizar las vistas en cada elemento de la lista con los datos correspondientes de la lista filtrada
        }

        override fun getItemCount(): Int = filteredList.size

        override fun getFilter(): Filter {
            return object : Filter() {
                override fun performFiltering(constraint: CharSequence?): FilterResults {
                    val searchText = constraint.toString().lowercase(Locale.ROOT)

                    filteredList = if (searchText.isEmpty() || plantList.isEmpty()) {
                        originalList.toList()
                    } else {
                        // get first 4 plants from plantList using the filter text
                        val results = plantList.filter {
                            it.lowercase(Locale.ROOT).contains(searchText)
                        }.take(4)

                        // create SearchItems taking into account that results may have less than 4 plants
                        listOf(SearchItems(
                            plantName1 = if (results.size >= 1) results[0] else "",
                            plantName2 = if (results.size >= 2) results[1] else "",
                            plantName3 = if (results.size >= 3) results[2] else "",
                            plantName4 = if (results.size >= 4) results[3] else "",
                        ))

                    }
                    Log.e(TAG, "filteredList: $filteredList")
                    val filterResults = FilterResults()
                    filterResults.values = filteredList
                    return filterResults
                }

                override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                    filteredList = results?.values as List<SearchItems>
                    notifyDataSetChanged()
                }
            }
        }
    }
