package ie.wit.fragments.pinFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ie.wit.R
import ie.wit.main.MainApp
import ie.wit.utils.getAllPins
import ie.wit.utils.getFavouritePins
import ie.wit.utils.setMapMarker
import kotlinx.android.synthetic.main.fragment_map_pins.*

class PinMapFragment : Fragment(){

    lateinit var app: MainApp
    var viewBookmarkedPins = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val layout = inflater.inflate(R.layout.fragment_map_pins, container, false)

        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = getString(R.string.pins_map_title)

        imageMapFavourites.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                app.mMap.clear()
                setMapMarker(app)
                if (!viewBookmarkedPins) {
                    imageMapFavourites.setImageResource(R.drawable.ic_bookmark_gold)
                    viewBookmarkedPins = true
                    getFavouritePins(app)
                }
                else {
                    imageMapFavourites.setImageResource(R.drawable.ic_bookmark_border_black_24dp)
                    viewBookmarkedPins = false
                    getAllPins(app)
                }
            }
        })
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            PinMapFragment().apply {
                arguments = Bundle().apply { }
            }
    }
}