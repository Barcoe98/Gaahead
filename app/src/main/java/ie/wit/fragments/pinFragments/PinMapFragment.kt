package ie.wit.fragments.pinFragments

import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import ie.wit.R
import ie.wit.main.MainApp
import ie.wit.utils.getAllPins
import ie.wit.utils.setMapMarker
import ie.wit.utils.trackLocation

class PinMapFragment : SupportMapFragment(), OnMapReadyCallback {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
        getMapAsync(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = getString(R.string.pins_title)
    }

    override fun onMapReady(googleMap: GoogleMap) {

        app.mMap = googleMap
        app.mMap.isMyLocationEnabled = true
        app.mMap.uiSettings.isZoomControlsEnabled = true
        app.mMap.uiSettings.setAllGesturesEnabled(true)
        app.mMap.clear()
        trackLocation(app)
        setMapMarker(app)
        getAllPins(app)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            PinMapFragment().apply {
                arguments = Bundle().apply { }
            }
    }
}