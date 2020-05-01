package ie.wit.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import ie.wit.R
import ie.wit.adapters.ClubAdapter
import ie.wit.fragments.clubFragments.ClubDetailsFragment
import ie.wit.fragments.clubFragments.ClubFavouritesFragment
import ie.wit.main.MainApp
import ie.wit.models.ClubModel
import ie.wit.utils.createLoader
import kotlinx.android.synthetic.main.fragment_edit_fixture.view.*
import kotlinx.android.synthetic.main.fragment_my_info.view.*
import kotlinx.android.synthetic.main.list_view.view.*
import org.jetbrains.anko.AnkoLogger


open class MyInfoFragment : Fragment(), AnkoLogger {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_my_info, container, false)
        activity?.title = getString(R.string.my_info_title)

        var query = FirebaseDatabase.getInstance()
            .reference
            .child("users").child(app.auth.currentUser!!.uid).orderByChild("userTpe").equalTo("Manager").toString()

        //root.Uname.setText(query)
        //root.uName.text = query

        onAddInfoClick(root)

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MyInfoFragment().apply {
                arguments = Bundle().apply { }
            }
    }

    fun onAddInfoClick(layout: View) {

        layout.addMyInfoBtn.setOnClickListener {

            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.homeFrame, UserInfoFragment.newInstance())
                .addToBackStack(null)
                .commit()
        }
    }


}


