package ie.wit.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ie.wit.R
import ie.wit.adapters.ResultAdapter
import ie.wit.main.MainApp
import ie.wit.models.ResultModel
import kotlinx.android.synthetic.main.fragment_result_list.*
import kotlinx.android.synthetic.main.fragment_result_list.view.*

class ResultListFragment : Fragment() {

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

        root.rRecyclerView.layoutManager = LinearLayoutManager(activity)
        root.rRecyclerView.adapter = ResultAdapter(app.resultsStore.findAll())

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
        rRecyclerView.adapter = ResultAdapter(results)
        rRecyclerView.adapter?.notifyDataSetChanged()
    }
}