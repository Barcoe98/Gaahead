package ie.wit.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ie.wit.R
import ie.wit.main.MainApp
import ie.wit.models.FixtureModel
import ie.wit.models.ResultModel
import kotlinx.android.synthetic.main.card_fixture.*
import kotlinx.android.synthetic.main.card_fixture.view.*
import kotlinx.android.synthetic.main.fragment_fixture.*
import kotlinx.android.synthetic.main.fragment_fixture.view.*
import kotlinx.android.synthetic.main.fragment_result.view.*
import org.jetbrains.anko.info
import org.jetbrains.anko.toast

class ResultFragment : Fragment() {

    lateinit var app: MainApp
    var result = ResultModel()
    private val imageRequest = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_result, container, false)
        activity?.title = getString(R.string.result_title)

        setResultButtonListener(root)
            //setImgBtnListener(root)
        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ResultFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    private fun setResultButtonListener(layout: View) {
        layout.addResultBtn.setOnClickListener {

            result.teamAName = teamAName.text.toString()
            result.teamBName = teamBName.text.toString()
            //result.type = teamBName.text.toString()

            when {
                result.teamAName.isEmpty()  ->  Toast.makeText(app,R.string.enter_teamAName, Toast.LENGTH_LONG).show()
                result.teamBName.isEmpty() ->  Toast.makeText(app,R.string.enter_teamBName, Toast.LENGTH_LONG).show()
                //result.type.isEmpty() ->  Toast.makeText(app,R.string.hint_type, Toast.LENGTH_LONG).show()

                else -> app.resultsStore.create(result.copy())

            }
            result.teamAName = teamAName.setText("").toString()
            result.teamBName = teamBName.setText("").toString()
            //result.type = teamBName.setText("").toString()

        }

    }
}