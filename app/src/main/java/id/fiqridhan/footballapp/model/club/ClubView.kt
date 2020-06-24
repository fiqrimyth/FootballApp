package id.fiqridhan.footballapp.model.club

import id.fiqridhan.footballapp.model.League

interface ClubView {

    fun showLoading()

    fun showClubList(data: List<League>)

    fun hideLoading()
}