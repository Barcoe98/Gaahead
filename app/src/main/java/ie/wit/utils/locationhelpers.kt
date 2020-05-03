package ie.wit.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import ie.wit.R
import ie.wit.main.MainApp
import ie.wit.models.PinModel

val REQUEST_PERMISSIONS_REQUEST_CODE = 34

fun checkLocationPermissions(activity: Activity) : Boolean {
    if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        return true
    }
    else {
        ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSIONS_REQUEST_CODE)
        return false
    }
}

fun isPermissionGranted(code: Int, grantResults: IntArray): Boolean {
    var permissionGranted = false
    if (code == REQUEST_PERMISSIONS_REQUEST_CODE) {
        when {
            grantResults.isEmpty() -> Log.i("Location", "User interaction was cancelled.")
            (grantResults[0] == PackageManager.PERMISSION_GRANTED) -> {
                permissionGranted = true
                Log.i("Location", "Permission Granted.")
            }
            else -> Log.i("Location", "Permission Denied.")
        }
    }
    return permissionGranted
}

@SuppressLint("MissingPermission")
fun setCurrentLocation(app: MainApp) {
    app.locationClient.lastLocation
        .addOnSuccessListener { location : Location? ->
            app.currentLocation = location!!
        }
}


@SuppressLint("RestrictedApi")
fun createDefaultLocationRequest() : LocationRequest {
    val locationRequest = LocationRequest().apply {
        interval = 10000
        fastestInterval = 5000
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }
    return locationRequest
}

@SuppressLint("MissingPermission")
fun trackLocation(app: MainApp) {
    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            if (locationResult != null) {
                app.currentLocation = locationResult.locations.last()
                app.marker.position = LatLng(app.currentLocation.latitude,
                    app.currentLocation.longitude)
            }
        }
    }

    app.locationClient.requestLocationUpdates(createDefaultLocationRequest(),
        locationCallback, null)
}

fun setMapMarker(app: MainApp) {

    val pos = LatLng(app.currentLocation.latitude,
        app.currentLocation.longitude)

    app.marker = app.mMap.addMarker(
        MarkerOptions().position(pos)
        .title("My Current Location")
        .snippet("This is Me!")
        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)))

    app.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 14f))
}


fun getAllPins(app: MainApp) {
    val pinsList = ArrayList<PinModel>()

    app.database.child("user-pins").child(app.auth.currentUser!!.uid)
        .addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {

                val children = snapshot.children
                children.forEach {
                    val pin = it.
                        getValue<PinModel>(PinModel::class.java)
                    pinsList.add(pin!!)
                }
                addMapMarkers(pinsList, app.mMap)
            }
        })

}

fun getFavouritePins(app: MainApp) {
    val pinsList = ArrayList<PinModel>()

    app.database.child("user-pins").child(app.auth.currentUser!!.uid)
        .orderByChild("isPinned")
        .equalTo(true)
        .addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {

                val children = snapshot.children
                children.forEach {
                    val pin = it.
                        getValue<PinModel>(PinModel::class.java)
                    pinsList.add(pin!!)
                }
                addMapMarkers(pinsList, app.mMap)
            }
        })

}

fun addMapMarkers(dl : ArrayList<PinModel>, map: GoogleMap) {
    dl.forEach {
        map.addMarker(
            MarkerOptions().position(LatLng(it.latitude, it.longitude))
                .title(it.title)
                .snippet(it.comment)
                .icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)
                )
        )
    }
}
