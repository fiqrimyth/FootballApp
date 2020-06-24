package id.fiqridhan.footballapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.fiqridhan.footballapp.fragment.ClubFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadClubFragment(savedInstanceState)
    }

    private fun loadClubFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null){
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.layout_main, ClubFragment(), ClubFragment::class.simpleName)
                    .commit()
        }
    }
}