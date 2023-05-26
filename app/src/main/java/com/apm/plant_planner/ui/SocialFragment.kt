package com.apm.plant_planner.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.apm.plant_planner.R
import com.apm.plant_planner.model.Plant
import com.apm.plant_planner.model.PlantAdapter
import com.apm.plant_planner.model.Post
import com.apm.plant_planner.model.PostAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [SocialFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SocialFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = requireContext().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        if (!sharedPreferences.contains("username")) {
            // Si el inventario está vacío, mostramos el fragmento NotSignInFragment
            val fragment = NotSignInFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.navHostFragment, fragment)
                .remove(this)
                .commit()
        }
    }

    val postList = mutableListOf<Post>()
    var postListString: String? = null
    var sharedPreferences: SharedPreferences? = null;


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val sharedPreferences1 = requireContext().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)

        if (sharedPreferences1.contains("username")) {


            // Inflate the layout for this fragment
            val view: View = inflater.inflate(R.layout.fragment_social, container, false)
            sharedPreferences = context?.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)

            getPlantPosts(
                view.context,
                { response ->
                    // Manejar la respuesta exitosa aquí
                    postListString = response.toString()

                    processPostListString()
                },
                { error ->
                    // Manejar el error aquí
                    println(error)
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                }
            )
            view.findViewById<FloatingActionButton>(R.id.floatingActionButton8).setOnClickListener {
                Log.d("btnSetup", "Selected")
                view.context.startActivity(Intent(view.context, Post::class.java))

            }

            //NOT IMPLEMENTED
            /*val searchFriendsBtn: Button = view.findViewById(R.id.add_user_btn)
        searchFriendsBtn.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.navHostFragment, SearchFriendsFragment())
            transaction.commit()
        }*/

            val postButton: FloatingActionButton = view.findViewById(R.id.floatingActionButton8)
            postButton.setOnClickListener {
                val transaction = parentFragmentManager.beginTransaction()
                transaction.replace(R.id.navHostFragment, PostFragment())
                transaction.commit()
            }

            return view;
        }
        return view;
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_social, container, false)
    }

        fun getAddressFromLocation(context: Context, locationString: String, onSuccess: (String) -> Unit, onError: (String) -> Unit) {
            val geocoder = Geocoder(context, Locale.getDefault())

            val locationParts = locationString.split(",")
            if (locationParts.size != 2) {
                onError("Invalid location format. Expected format: 'latitude,longitude'")
                return
            }

            val longitude = locationParts[0].toDoubleOrNull()
            val latitude = locationParts[1].toDoubleOrNull()

            if (latitude == null || longitude == null) {
                onError("Invalid latitude or longitude")
                return
            }

            try {
                val addressList: List<Address> = geocoder.getFromLocation(latitude, longitude, 1) as List<Address>
                if (addressList.isNotEmpty()) {
                    val address: Address = addressList[0]
                    val addressLine = address.getAddressLine(0)
                    onSuccess(addressLine)
                } else {
                    onError("Address not found for the given location")
                }
            } catch (e: IOException) {
                onError("Error fetching address: ${e.message}")
            }
        }

    fun processPostListString() {
        if (postListString != null) {
            println("sacon ifnomasd: " + postListString)
            val gson = Gson()
            val jsonArray = gson.fromJson(postListString, Array<Array<Any>>::class.java)


            for (array in jsonArray) {
                val id = array[0] as Double
                val title = array[1] as String
                val location = array[2] as String
                val bitmap = array[3] as String
                val author = array[6] as String

                val address = getAddressFromLocation(
                    requireContext(),
                    locationString = location,
                    onSuccess = { address ->
                        // Aquí puedes utilizar la dirección obtenida
                        println("Address: $address")
                        val post = Post(id, title, address.toString(), bitmap, author)
                        postList.add(post)
                    },
                    onError = { error ->
                        // Aquí puedes manejar el error
                        println("Error: $error")
                    }
                )
                println("Addresasdfa sdafs s: $address")


            }

            println("sacon postsArray: " + jsonArray)


            //val collectionType = object : TypeToken<List<Post?>?>() {}.type
            //val postList: List<Post> = Gson()
            //    .fromJson(postsArray.toString(), collectionType) as List<Post>



            /*val postsArray = jsonObject.getAsJsonArray("posts")
            val postList = postsArray.map { gson.fromJson(it, Post::class.java) }
            val postListJsonOutput = Gson().toJson(postList)*/




            //sharedPreferences?.edit()?.putString("post_list", postListJsonOutput)?.apply()
            if (postList.isEmpty()) {
                // si social esta vacío, mostramos el fragment de EmptySocial
                /*requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.navHostFragment, EmptyInventoryFragment())
                    .commit()*/
                println("Social SI vacío, mostrando social")
                println("DEBUG: post list: $postList")


            } else {
                // si el inventario no esta vacío, mostramos el fragment de Inventory
                println("Social no vacío, mostrando social")
                println("DEBUG: post list: $postList")
                val posts = view?.findViewById<RecyclerView>(R.id.recyclerView)
                if (posts != null) {
                    println("PRIMEROROROROROOR")
                    posts.layoutManager = LinearLayoutManager(context)
                } else {
                    println("PRIMEROROROROROOR 11111111111")
                }
                if (posts != null) {
                    println("PRIMEROROROROROOR 22222222222222222")
                    posts.adapter = PostAdapter(requireContext(), postList)
                } else {
                    println("PRIMEROROROROROOR 333333333333333333333")
                }
            }
        } else {
            // El resultado aún no está disponible
            println("lista de post NULL")
        }
    }

    fun getPlantPosts(context: Context, onResponse: (JSONArray) -> Unit, onError: (String) -> Unit) {
        val queue = Volley.newRequestQueue(context)
        val baseUrl = "http://10.0.2.2:8000"
        val url = "$baseUrl/plant/post"

        val request = object: JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                // Llamar a la función onResponse con la respuesta exitosa
                onResponse(response)
            },
            { error ->
                // Llamar a la función onError con el mensaje de error
                onError(" HOLALALALA:::: $error.toString()")
            }
        ){
            override fun getHeaders(): MutableMap<String, String> {
                val sharedPreferences = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
                val token = sharedPreferences.getString("token", "")
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer ".plus(token)
                return headers
            }
        }

        queue.add(request)
    }


}