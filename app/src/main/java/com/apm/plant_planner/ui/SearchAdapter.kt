package com.apm.plant_planner.ui;

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.apm.plant_planner.R
import java.util.Locale

class SearchAdapter (private val originalList: List<SearchItem>) : RecyclerView.Adapter<SearchViewHolder>(),
    Filterable {

        private var plantList : List<String> = emptyList()

        private var filteredList: List<SearchItem> = originalList.toList()

        fun updatePlantList(plantList: List<String>) {
            this.plantList = plantList
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.search_item, parent, false)
            return SearchViewHolder(itemView)
            // Aquí puedes inflar el diseño de cada elemento de la lista y devolver una instancia de MyViewHolder
        }

        override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
            val item = filteredList[position]
            holder.create_items(item)
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
                        }

                        // create a list of SearchItem objects from the results list
                        var listfiltered = mutableListOf<SearchItem>()
                        for (i in 0 until results.size) {
                            // Add a new SearchItem object to listfiltered for each plant in results
                            listfiltered.add(SearchItem(plantName = results[i]))
                        }

                        // return the list of SearchItem objects
                        listfiltered

                    }
                    Log.e(TAG, "filteredList: $filteredList")
                    val filterResults = FilterResults()
                    filterResults.values = filteredList
                    return filterResults
                }

                override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                    if (results?.values!=null) {
                        filteredList = results?.values as List<SearchItem>
                        notifyDataSetChanged()
                    }
                }
            }
        }
    }
