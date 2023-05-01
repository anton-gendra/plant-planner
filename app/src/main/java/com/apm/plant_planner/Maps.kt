package com.apm.plant_planner

import android.Manifest
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.apm.plant_planner.R
import com.google.android.gms.maps.*

class Maps : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener, ActivityCompat.OnRequestPermissionsResultCallback {

    private lateinit var map: GoogleMap

    companion object{
        const val REQUEST_CODE_LOCATION=0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Retrieve the content view that renders the map.
        setContentView(R.layout.fragment_maps)

        // Get the SupportMapFragment and request notification when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        Log.d("btnSetup", "Selected 111111111111111")

        createMarker()
        addNewMarker()

        map.setOnMyLocationButtonClickListener(this)
        map.setOnMyLocationClickListener(this)
        enableLocation()
    }

    private fun addNewMarker() {
        map.setOnMapClickListener(object :GoogleMap.OnMapClickListener {
            override fun onMapClick(latlng :LatLng) {
                // Clears the previously touched position
                map.clear();
                // Animating to the touched position
                map.animateCamera(CameraUpdateFactory.newLatLng(latlng));

                val location = LatLng(latlng.latitude,latlng.longitude)
                    map.addMarker(MarkerOptions()
                        .snippet(latlng.latitude.toString()+ " : " + latlng.longitude)
                        .title("Planta")
                        .position(location))
            }
        })
    }

    private fun createMarker() {
        val sydney = LatLng(-33.852, 151.211)
        map.addMarker(
            MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney")
                .draggable(true)
        )
        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(sydney, 18f),
            4000,
            null
        )
    }

    private fun isLocationPermissionGranted() = ContextCompat.checkSelfPermission(
    this,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    private fun enableLocation() {
        //map no actualizado -> out
        if(!::map.isInitialized) return
        //Si los permisos de localizacion estan activos -> activo localizacion en tiempo real
        if(isLocationPermissionGranted()) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            map.isMyLocationEnabled = true
            Log.d("btnSetup", "Selected 111111111111111 111111111")

        } else {
            //Si no estan activos, pido los permisos
            Log.d("btnSetup", "Selected 111111111111111 22222")
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        //Si se pidieron los permisos pero los ha rechazado, sacamos un mensaje para que los active el usuario mismo
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            Toast.makeText(this, "Ve a ajustes y acepta los permisos de ubicación", Toast.LENGTH_SHORT).show()
        } else {
            // Sino, se los pedimos
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            //Si los acepta, le activamos la localizacion
            REQUEST_CODE_LOCATION -> if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return
                }
                map.isMyLocationEnabled = true
            } else {
                //Sino, mostramos otro mensaje
                Toast.makeText(this, "Ajustes -> Acepta permisos", Toast.LENGTH_SHORT).show()

            } else ->{}
        }
    }

    override fun onResumeFragments() {
        //Si cojo y desactivo los permisos de esa app para la localizacion en el dispositivo:
            //Comprobamos que los permisos siguen activos, si se puso la app en background,
                //si cambiamos de app, etc...
        super.onResumeFragments()
        if (!::map.isInitialized) return
        if(!isLocationPermissionGranted()){
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            map.isMyLocationEnabled = false

            Toast.makeText(this, "Para activar la localización ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onMyLocationButtonClick(): Boolean {
        //Mensaje, cuando pulsemos en el boton de arriba a la derecha para ir a nuestra localizacion
        Toast.makeText(this, "Boton pulsado", Toast.LENGTH_SHORT).show()
        return false
    }

    override fun onMyLocationClick(p0: Location) {
        //Mensaje, con info de la localizacion, cuando pulsamos en nuestra localizacion
        Toast.makeText(this, "Estás en ${p0.latitude}, ${p0.longitude}", Toast.LENGTH_SHORT).show()
    }

    //Nota:
    //A veces al compilar luego de un rato, no se por que sale un error y no carga el map para
    //la localizacion, la solucion que hago es: en el androidManifest: elimino la fila de: ACCESS_FINE_LOCATION
    //Ejecuto y ya ejecuta, pero ahi para volver a pedir ese permiso. Paro el emulador, y pongo otra vez la linea borrada
    //Y ahora ya me pide los permisos otra vez, acepto, y todo funciona OK

//https://developers.google.com/maps/documentation/android-sdk/map-with-marker?hl=es-419
}