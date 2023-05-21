package com.apm.plant_planner.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.apm.plant_planner.LoginActivity
import com.apm.plant_planner.Maps
import com.apm.plant_planner.R
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PostFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PostFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var title: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            //getLocation of post plant
            param1 = it.getString("location")
            param2 = it.getString("title")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_post, container, false)

        val editTitle = view?.findViewById<EditText>(R.id.editTextTitle);
        view.findViewById<Button>(R.id.addLocationPost).setOnClickListener {
            val enteredTitle = editTitle?.text.toString().trim()
            if(enteredTitle.isNotEmpty()) {
                title = enteredTitle
                Toast.makeText(context, "Title: $title - Location: $param1", Toast.LENGTH_SHORT).show()
                val intent = Intent(view.context, Maps::class.java)
                val bundle = Bundle()
                bundle.putString("title", title)
                intent.putExtras(bundle)
                startActivity(intent)
            } else {
                Toast.makeText(context, "Ingrese un título válido", Toast.LENGTH_SHORT).show()
            }

        }

        val textViewName = view?.findViewById<TextView>(R.id.editTextTextPersonName3)
        textViewName?.text = param1
        val textTitle = view?.findViewById<TextView>(R.id.editTextTitle)
        textTitle?.text = param2

        val publishButton: Button = view.findViewById(R.id.publishButton)
        publishButton.setOnClickListener {
            Toast.makeText(context, "Title: $param2 - Location: $param1", Toast.LENGTH_SHORT).show()
            //llamada al back! hacer en post con todos los datos, no aqui solo con las coordenadas


            val queue = Volley.newRequestQueue(context)
            val url = "http://10.0.2.2:8000/plant/post"

            val body = JSONObject()
            body.put("title", param1)
            body.put("location", param2)
            body.put("image", "image1")
            body.put("author", 1)


            val request = object: JsonObjectRequest(
                Method.POST, url, body,
                { response ->

                    Toast.makeText(context, "Register data: ".plus(response.toString()), Toast.LENGTH_SHORT).show()

                },
                { error ->
                    Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
                }
            ) {}

            queue.add(request)
        }
        // Inflate the layout for this fragment


        return view
    }

     fun updateTitleInView() {
        val textViewTitle = view?.findViewById<TextView>(R.id.editTextTitle)
        textViewTitle?.text = param2
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PostFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PostFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}