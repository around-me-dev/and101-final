import android.content.Context
import android.location.Location
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

//


        map.isMyLocationEnabled = true  // Ensure permissions are checked before calling this
    }

    fun moveCameraToLocation(location: Location) {
        val userCoordinates = LatLng(location.latitude, location.longitude)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(userCoordinates, 15f))
    }

    fun addMarkers(locations: List<LatLng>) {
        for (location in locations) {
            map.addMarker(MarkerOptions().position(location))
        }
    }

}
