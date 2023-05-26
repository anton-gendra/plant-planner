package com.apm.plant_planner.ui;

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.apm.plant_planner.R
import com.google.gson.Gson
import java.util.Locale

class SearchAdapter (private val originalList: List<SearchItem>) : RecyclerView.Adapter<SearchViewHolder>(),
    Filterable {

        private var plantList : List<String> = emptyList()

        private var filteredList: List<SearchItem> = originalList.toList()

        private var onClickListener: OnClickListener? = null

        var queue: RequestQueue? = null

        // onClickListener Interface
        interface OnClickListener {
            fun onClick(position: Int, model: SearchItem)
        }

        fun setOnClickListener(onClickListener: OnClickListener) {
            this.onClickListener = onClickListener
        }

        fun updatePlantList(plantList: List<String>) {
            this.plantList = plantList
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
            queue = Volley.newRequestQueue(parent.context)
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.search_item, parent, false)
            return SearchViewHolder(itemView)
            // Aquí puedes inflar el diseño de cada elemento de la lista y devolver una instancia de MyViewHolder
        }

        override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
            val item = filteredList[position]
            holder.create_items(item)
            holder.itemView.setOnClickListener {
                if (onClickListener != null) {
                    onClickListener!!.onClick(position, item)
                }
            }
        }

        override fun getItemCount(): Int = filteredList.size

        override fun getFilter(): Filter {
            return object : Filter() {
                override fun performFiltering(constraint: CharSequence?): FilterResults {
                    val searchText = constraint.toString().lowercase(Locale.ROOT)

                    filteredList = if (searchText.isEmpty() || plantList.isEmpty()) {
                        originalList.toList()
                    } else {
                        val results = plantList.filter {
                            it.lowercase(Locale.ROOT).contains(searchText)
                        }
                        // create a list of SearchItem objects from the results list
                        var listfiltered = mutableListOf<SearchItem>()
                        for (i in 0 until results.size) {
                            // Add a new SearchItem object to listfiltered for each plant in results
                            if (i <= 2){

                                val url = "https://serpapi.com/search.json?q="+results[i]+"&engine=google_images&ijn=0&api_key=950e962b443b33032fb39a8002b2c4111245b51d76329042f50953cd995a2777"

                                // Request a string response from the provided URL.
                                val stringRequest = object : StringRequest(
                                    Method.GET, url,
                                    Response.Listener { response ->
                                        //println("Response is: $response")
                                        val gson = Gson()
                                        //GET THE URL of the "thumbnail": "<URL to image>" field of the json
                                        response.toString().substringAfter("thumbnail\": \"").substringBefore("\"").let {
                                            println(it)
                                            listfiltered.add(SearchItem(plantName = results[i], plantImage = it))
                                        }
                                    },
                                    Response.ErrorListener { error ->
                                        println(error.toString())
                                        //Toast.makeText(requireContext(), "Error al realizar la petición", Toast.LENGTH_SHORT).show()
                                        println("Error al realizar la petición")
                                    }
                                ) {
                                }
                                stringRequest.retryPolicy = DefaultRetryPolicy(6000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
                                queue?.add(stringRequest)
                            } else{
                                listfiltered.add(SearchItem(plantName = results[i], plantImage = "https://es.wikipedia.org/wiki/Ferocactus#/media/Archivo:Ferocactus_cylindraceus_1.jpg"))
                            }
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
