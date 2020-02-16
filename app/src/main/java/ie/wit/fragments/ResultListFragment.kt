@file:Suppress("UNREACHABLE_CODE")

package ie.wit.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ie.wit.R
import ie.wit.activities.FixtureActivity
import ie.wit.activities.ResultActivity
import ie.wit.adapters.ResultAdapter
import ie.wit.adapters.ResultListener
import ie.wit.main.MainApp
import ie.wit.models.ResultModel
import kotlinx.android.synthetic.main.fragment_result_list.*
import kotlinx.android.synthetic.main.fragment_result_list.view.*

class ResultListFragment : Fragment(), ResultListener {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_result_list, container, false)
        activity?.title = getString(R.string.result_title)

        root.rRecyclerView.layoutManager = LinearLayoutManager(activity)
        root.rRecyclerView.adapter = ResultAdapter(app.resultsStore.findAll(),this)
        return root

        //Loads Results from json file
        loadResults()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ResultListFragment().apply {
                arguments = Bundle().apply { }
            }
    }


    private fun loadResults() {
        showResults(app.resultsStore.findAll())
    }

    private fun showResults (results: List<ResultModel>) {
        rRecyclerView.adapter = ResultAdapter(results,this)
        rRecyclerView.adapter?.notifyDataSetChanged()
    }
/*
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loadResults()
        super.onActivityResult(requestCode, resultCode, data)
    }

 */

    override fun onResultClick(result: ResultModel) {
        val intent = Intent(activity, ResultActivity::class.java).putExtra("result_edit", result)
        startActivity(intent)
        loadResults()
    }
}