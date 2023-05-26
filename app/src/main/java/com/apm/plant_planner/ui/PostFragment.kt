package com.apm.plant_planner.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.apm.plant_planner.LoginActivity
import com.apm.plant_planner.Maps
import com.apm.plant_planner.R
import org.json.JSONObject
import java.io.ByteArrayOutputStream

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
    private var bitmap: Bitmap? = null
    private var base64Image: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            //getLocation of post plant
            param1 = it.getString("location")
            param2 = it.getString("title")
        }
    }

    // Get your image
    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result?.data != null && view != null) {
                    bitmap = result.data?.extras?.get("data") as Bitmap
                    val plant_image = view?.findViewById<ImageView>(R.id.imageView2)
                    plant_image?.setImageBitmap(bitmap)
                }
            }
        }

    @SuppressLint("WrongThread")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_post, container, false)

        val btnTakePhoto: Button? = view.findViewById(R.id.camaraPost)
        if (btnTakePhoto != null) {
            btnTakePhoto.visibility = View.GONE
            if(param1 != null) {
                btnTakePhoto.visibility = View.VISIBLE
            }
        };
        val galeriaButton: Button? = view.findViewById(R.id.galeriaPost)
        if (galeriaButton != null) {
            galeriaButton.visibility = View.GONE
            if(param1 != null) {
                galeriaButton.visibility = View.VISIBLE
            }
        };

        // Boton para la camara
        if (btnTakePhoto != null) {
            btnTakePhoto.setOnClickListener {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                resultLauncher.launch(cameraIntent)
            }
        }

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
            //Comprobar imagen
            if (bitmap != null) {
                val outputStream = ByteArrayOutputStream()
                bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                val imageByteArray: ByteArray = outputStream.toByteArray()
                base64Image = Base64.encodeToString(imageByteArray, Base64.DEFAULT)

            }
            val sharedPreferences = context?.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
            val userId = sharedPreferences?.getInt("userid", 0)
            Toast.makeText(context, "UserId: $userId - Title: $param2 - Location: $param1", Toast.LENGTH_SHORT).show()

            val queue = Volley.newRequestQueue(context)
            val url = "http://10.0.2.2:8000/plant/post"

            val body = JSONObject()
            body.put("title", param2)
            body.put("location", param1)
            body.put("image", base64Image)
            body.put("author", userId)

            val request = JsonObjectRequest(
                Request.Method.POST, url, body,
                { response ->
                    Toast.makeText(context, "Post data: " + response.toString(), Toast.LENGTH_SHORT).show()
                },
                { error ->
                    Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
                }
            )

            queue.add(request)

            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.postLayout, SocialFragment())
            transaction.commit()

        }

        //AQUI COJO EL BASE64 DE LA BD Y MUESTRA LA IMAGEN EN EL OBJ XML DE IMAGEVIEW
        //HACERLO EN SOCIAL
        /*val imageByteArray = Base64.decode(base64Image, Base64.DEFAULT)
        val bitmap2 = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.size)
        val imageView: ImageView = view.findViewById(R.id.imageView3)
        imageView.setImageBitmap(bitmap2)*/

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