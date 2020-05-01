package ie.wit.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ie.wit.R
import ie.wit.main.MainApp
import org.jetbrains.anko.AnkoLogger


open class AppInfoFragment : Fragment(), AnkoLogger {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_app_info, container, false)
        activity?.title = getString(R.string.app_info_title)

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            AppInfoFragment().apply {
                arguments = Bundle().apply { }
            }
    }
}


