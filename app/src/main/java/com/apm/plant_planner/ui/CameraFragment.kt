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
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.apm.plant_planner.R
import com.apm.plant_planner.SearchPlant

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CameraFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CameraFragment : Fragment() {

    private var bitmap: Bitmap? = null
    private var view: View? = null

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

    private fun sendImage() {
        //enviar bitmap al servidor
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val viewLoc = inflater.inflate(R.layout.fragment_camera, container, false)
        view = viewLoc // la global para el result launches
        val btnTakePhoto: Button = viewLoc.findViewById(R.id.btnTakePhoto)
        val btnDetectar: Button = viewLoc.findViewById(R.id.detectar)

        // Boton para la camara
        btnTakePhoto.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            resultLauncher.launch(cameraIntent)
        }

        // Boton para enviar imagen
        btnDetectar.setOnClickListener {
            if (bitmap != null) {
                sendImage()
            } else {
                Toast.makeText(requireContext(), "No hay una imagen disponible.", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}