import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import com.example.aroundme.MainActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapManager(private val context: Context) {
    private lateinit var map: GoogleMap

    fun initializeMap(googleMap: GoogleMap) {
        this.map = googleMap
        setupMap()
    }

    private fun setupMap() {
        map.uiSettings.isZoomControlsEnabled = true

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            map.isMyLocationEnabled = true
        } else {
            ActivityCompat.requestPermissions(
                context as MainActivity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                2
            )
        }
    }



//    fun addMarkers(locations: List<LatLng>) {
//        for (location in locations) {
//            map.addMarker(MarkerOptions().position(location))
//        }
//    }

}
