package id.fiqridhan.footballapp.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import id.fiqridhan.footballapp.adapter.ClubAdapter
import id.fiqridhan.footballapp.R
import id.fiqridhan.footballapp.model.club.ClubPresenter
import id.fiqridhan.footballapp.model.club.ClubView
import id.fiqridhan.footballapp.model.League
import id.fiqridhan.footballapp.network.ApiRepository
import id.fiqridhan.footballapp.view.DetailActivityClub
import kotlinx.android.synthetic.main.fragment_club.view.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.onRefresh

class ClubFragment : Fragment(), ClubView {

    private var teams: MutableList<League> = mutableListOf()
    private lateinit var presenter: ClubPresenter
    private lateinit var adapter: ClubAdapter
    private lateinit var leagueName: String
    private lateinit var listTeam: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var spinner: Spinner
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater.inflate(R.layout.fragment_club, container, false)
        val activity = activity
        adapter = ClubAdapter(context, teams) {
            startActivity(
                intentFor<DetailActivityClub>(
                    "NAME_ITEM" to it.teamName,
                    "DESC_ITEM" to it.teamDescription,
                    "IMG_ITEM" to it.teamBadge
                )
            )
        }

        listTeam = rootView.findViewById<RecyclerView>(R.id.lrv_view_club)
        listTeam.layoutManager = LinearLayoutManager(activity)
        listTeam.adapter = adapter

        spinner = rootView.l_spinner
        swipeRefreshLayout = rootView.l_swipe
        progressBar = rootView.l_progressBar

        val spinnerItems = resources.getStringArray(R.array.league)
        spinner.adapter =  ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                leagueName = spinner.selectedItem.toString()
                presenter.getClubList(leagueName)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        swipeRefreshLayout.onRefresh {
            presenter.getClubList(leagueName)
        }
        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW)

        val request = ApiRepository()
        val gson = Gson()
        presenter = ClubPresenter(
            this,
            request,
            gson
        )
        return rootView
    }


    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.INVISIBLE
    }

    override fun showClubList(data: List<League>) {
        swipeRefreshLayout.isRefreshing = false
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()
    }
}