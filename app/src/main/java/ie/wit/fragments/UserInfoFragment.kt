package ie.wit.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import ie.wit.R
import ie.wit.main.MainApp
import ie.wit.models.UserModel
import ie.wit.utils.createLoader
import ie.wit.utils.hideLoader
import ie.wit.utils.showLoader
import kotlinx.android.synthetic.main.fragment_edit_fixture.view.*
import kotlinx.android.synthetic.main.fragment_user_info.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class UserInfoFragment : Fragment(), AnkoLogger {

    lateinit var app: MainApp
    lateinit var loader : AlertDialog
    lateinit var root: View
    var editUser: UserModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp

        arguments?.let {
            //editUser = it.getParcelable("edituser")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_user_info, container, false)
        activity?.title = getString(R.string.user_info_title)
        loader = createLoader(activity!!)

        // fragment ////////////////  model///////////
        //root.name.setText("")
//        root.editTeamAName.setText(editUser!!.name)

        root.addManagerInfoBtn.setOnClickListener {
            showLoader(loader, "Updating User on Server...")
            //updateUserData()
            //updateUser(editUser!!.uid, editUser!!)
        }

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            UserInfoFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    fun updateUserData() {
        //////////model//////Fragment/////////

        editUser?.name = root.name.text.toString()

    }


    fun updateUser(uid: String?, user: UserModel) {
        app.database.child("users").child(uid!!)
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.ref.setValue(user)
                        ////////////////////////////////////////////////
                        activity!!.supportFragmentManager.beginTransaction()
                            .replace(R.id.homeFrame,
                                MyInfoFragment.newInstance())
                            .addToBackStack(null)
                            .commit()
                        ///////////////////////////////////////////////
                        hideLoader(loader)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        info("Firebase User error : ${error.message}")
                    }
                })
    }

}

