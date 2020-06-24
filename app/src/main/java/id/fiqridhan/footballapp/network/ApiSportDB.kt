package id.fiqridhan.footballapp.network

import id.fiqridhan.footballapp.BuildConfig

object ApiTheSportDB {
    fun getClub(league: String?): String {
        val league1 = league?.replace(" ", "%20")
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.API_KEY}" + "/search_all_teams.php?l=" + league1
    }
}