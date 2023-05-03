package com.apm.plant_planner.ui;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest

import com.android.volley.toolbox.Volley;
import com.apm.plant_planner.R
import com.apm.plant_planner.SearchPlant
import java.util.Locale

class SearchAdapter (private val originalList: List<SearchItems>) : RecyclerView.Adapter<SearchAdapter.MyViewHolder>(),
    Filterable {


    // obtener lista de especies (no es lo que hay que hacer, pero estoy probando)
    // TODO: podemos utilizar este endpoint para tener una lista de especies que se usa en la app
    // asi si el usuario quiere buscar especies en vez de sacar foto ya tneemos una lista de ellas
    // y con los mismos nombres que los que detectara la camara (no se si me explique, soy angel)
    val url = "https://my-api.plantnet.org/v2/species?api-key=2b101nCSCBq24oD2NvMubv3gu"

    // Request a string response from the provided URL.
    val stringRequest = object : StringRequest(
            Request.Method.GET, url,
            Response.Listener { response ->
            // Display the first 500 characters of the response string.
            //Toast.makeText(requireContext(), "Response is: ${response.substring(0, 500)}", Toast.LENGTH_SHORT).show()
            println("Response is: $response")
                val gson = Gson()
                val searchItemsList = gson.fromJson(response, Array<SearchItems>::class.java).toList()
    },
            Response.ErrorListener {
        //Toast.makeText(requireContext(), "Error al realizar la petición", Toast.LENGTH_SHORT).show()
        println("Error al realizar la petición")
    }
            ) {
        // Override del método getHeaders() para agregar el encabezado Authorization
        override fun getHeaders(): MutableMap<String, String> {
            val headers = HashMap<String, String>()
            val apiKey = "2b10HxNDLtr5CbwAVr04hDaVwe"
            headers["Authorization"] = "Bearer $apiKey"
            headers["accept"] = "application/json"
            return headers
        }
    }

        private var filteredList: List<SearchItems> = originalList.toList()

        inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val textView1: TextView = itemView.findViewById(R.id.textView18)
            val textView2: TextView = itemView.findViewById(R.id.textView19)
            val textView3: TextView = itemView.findViewById(R.id.textView20)
            val textView4: TextView = itemView.findViewById(R.id.textView21)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_search, parent, false)
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
                    val searchText = constraint.toString().toLowerCase(Locale.ROOT)
                    filteredList = if (searchText.isEmpty()) {
                        originalList.toList()
                    } else {
                        originalList.filter {
                            it.plantName1.toLowerCase(Locale.ROOT).contains(searchText) ||
                                    it.plantName2.toLowerCase(Locale.ROOT).contains(searchText) ||
                                    it.plantName3.toLowerCase(Locale.ROOT).contains(searchText) ||
                                    it.plantName4.toLowerCase(Locale.ROOT).contains(searchText)
                        }
                    }
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
