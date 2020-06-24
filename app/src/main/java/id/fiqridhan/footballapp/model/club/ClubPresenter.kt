package id.fiqridhan.footballapp.model.club

import com.google.gson.Gson
import id.fiqridhan.footballapp.model.LeagueResponse
import id.fiqridhan.footballapp.network.ApiRepository
import id.fiqridhan.footballapp.network.ApiTheSportDB
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class ClubPresenter(
    private val view: ClubView,
    private val apiRespository: ApiRepository,
    private val gson: Gson
) {

    fun getClubList(league: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(
                apiRespository
                    .doRequest(ApiTheSportDB.getClub(league)),
                LeagueResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showClubList(data.teams)
            }
        }
    }

}