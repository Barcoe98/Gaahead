@file:Suppress("UNREACHABLE_CODE")

package ie.wit.fragments
/*
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ie.wit.R

import ie.wit.adapters.PlayerAdapter
import ie.wit.adapters.PlayerListener
import ie.wit.adapters.ResultAdapter
import ie.wit.adapters.ResultListener
import ie.wit.main.MainApp
import ie.wit.models.PlayerModel
import ie.wit.models.ResultModel
import kotlinx.android.synthetic.main.fragment_player_list.*
import kotlinx.android.synthetic.main.fragment_player_list.view.*
import kotlinx.android.synthetic.main.fragment_result_list.*
import kotlinx.android.synthetic.main.fragment_result_list.view.*

class PlayerListFragment : Fragment(), PlayerListener {

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
        var root = inflater.inflate(R.layout.fragment_player_list, container, false)
        activity?.title = getString(R.string.player_title)

        root.pRecyclerView.layoutManager = LinearLayoutManager(activity)
        root.pRecyclerView.adapter = PlayerAdapter(app.playersStore.findAll(),this)
        return root

        //Loads Players from json file
        loadPlayers()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            PlayerListFragment().apply {
                arguments = Bundle().apply { }
            }
    }


    private fun loadPlayers() {
        showPlayers(app.playersStore.findAll())
    }

    private fun showPlayers (players: List<PlayerModel>) {
        pRecyclerView.adapter = PlayerAdapter(players,this)
        pRecyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onPlayerClick(player: PlayerModel) {
        val intent = Intent(activity, PlayerActivity::class.java).putExtra("player_edit", player)
        startActivity(intent)
        loadPlayers()
    }
}


 */