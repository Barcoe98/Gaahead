package ie.wit.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ie.wit.R
import ie.wit.main.MainApp
import ie.wit.models.FixtureModel
import kotlinx.android.synthetic.main.card_fixture.*
import kotlinx.android.synthetic.main.card_fixture.view.*
import kotlinx.android.synthetic.main.fragment_fixture.*
import kotlinx.android.synthetic.main.fragment_fixture.view.*
import kotlinx.android.synthetic.main.fragment_fixture_list.*
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class FixtureFragment : Fragment() {

    lateinit var app: MainApp
    var fixture = FixtureModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_fixture, container, false)
        activity?.title = getString(R.string.fixture_title)
        setButtonListener(root)
        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FixtureFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    fun setButtonListener( layout: View) {
        layout.addFixtureBtn.setOnClickListener {

            fixture.teamAName = teamAName.text.toString()
            fixture.teamBName = teamBName.text.toString()
            fixture.date = date.text.toString()
            fixture.location = location.text.toString()
            fixture.time = time.text.toString()
            
            app.fixturesStore.create(fixture.copy())

        }
    }


}