package com.apm.plant_planner.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.apm.plant_planner.PlantAtributtes
import com.apm.plant_planner.R
import com.apm.plant_planner.utils.VolleyMultipartRequest
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import java.io.ByteArrayOutputStream

class CameraFragment : Fragment() {

    private var bitmap: Bitmap? = null
    private var view: View? = null

    var progressBar: ProgressBar? = null
    var detectar: Button? = null

    // Get your image
    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result?.data != null && view != null) {
                    bitmap = result.data?.extras?.get("data") as Bitmap
                    val plant_image = view?.findViewById<ImageView>(R.id.plantPreview)
                    plant_image?.setImageBitmap(bitmap)
                }
            }
        }

    private fun sendImage() = runBlocking {

        val apiKey = "2b10HxNDLtr5CbwAVr04hDaVwe"
        val url = "https://my-api.plantnet.org/v2/identify/all?include-related-images=false&no-reject=false&lang=es&api-key=$apiKey"

        val queue = Volley.newRequestQueue(requireContext())

        //val byteArrayOutputStream = ByteArrayOutputStream()
        //bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        //val imageByteArray = byteArrayOutputStream.toByteArray()

        val multipartRequest = object : VolleyMultipartRequest(
            Method.POST, url,
            Response.Listener { response ->
                val jsonString = String(response.data, Charsets.UTF_8)
                val jsonResponse = JSONObject(jsonString)
                val bestMatch = jsonResponse.getString("bestMatch")

                val intent = Intent(activity, PlantAtributtes::class.java)
                intent.putExtra("EXTRA_MODE", "new")
                intent.putExtra("EXTRA_TYPE", bestMatch)
                intent.putExtra("EXTRA_NAME", bestMatch)
                intent.putExtra("EXTRA_BITMAP", bitmap)
                startActivity(intent)
            },
            Response.ErrorListener { error ->
                progressBar?.visibility = View.GONE
                detectar?.visibility = View.VISIBLE
                Toast.makeText(requireContext(), "Error al realizar la petición: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer $apiKey"
                headers["accept"] = "application/json"
                return headers
            }

            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["organs"] = "flower"
                return params
            }

            override fun getByteData(): MutableMap<String, DataPart> {
                val params = HashMap<String, DataPart>()
                val fileName = "image.jpg"
                val byteArray = ByteArrayOutputStream()
                bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, byteArray)
                params["images"] = DataPart(fileName, byteArray.toByteArray(), "image/jpeg")
                return params
            }
        }

        // Agregar la solicitud a la cola de solicitudes
        queue.add(multipartRequest)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val plant_image = view?.findViewById<ImageView>(R.id.plantPreview)
        plant_image?.setImageBitmap(bitmap)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val viewLoc = inflater.inflate(R.layout.fragment_camera, container, false)
        view = viewLoc // la global para el result launches
        val btnTakePhoto: Button = viewLoc.findViewById(R.id.btnTakePhoto)
        val btnGallery: Button = viewLoc.findViewById(R.id.galeria)
        val btnDetectar: Button = viewLoc.findViewById(R.id.detectar)

        progressBar = view?.findViewById<ProgressBar>(R.id.progressBar)
        detectar = view?.findViewById<Button>(R.id.detectar)

        // Boton para la camara
        btnTakePhoto.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            resultLauncher.launch(cameraIntent)
        }

        btnGallery.setOnClickListener {
            Toast.makeText(requireContext(), R.string.no_implementation, Toast.LENGTH_SHORT).show()
        }

        // Boton para enviar imagen
        btnDetectar.setOnClickListener {
            if (bitmap != null) {
                detectar?.visibility = View.GONE
                progressBar?.visibility = View.VISIBLE
                sendImage()
            } else {
                Toast.makeText(requireContext(), "No hay una imagen disponible.", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}